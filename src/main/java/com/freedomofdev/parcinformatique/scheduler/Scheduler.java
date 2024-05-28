package com.freedomofdev.parcinformatique.scheduler;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import com.freedomofdev.parcinformatique.service.DemandeReparationService;
import com.freedomofdev.parcinformatique.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private DemandeReparationService demandeReparationService;

    @Autowired
    private DemandeReparationRepository demandeReparationRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private ActifRepository actifRepository;

    @Scheduled(cron = "0 0 0 * * ?") // run every day at 00:00
    public void sendReminderForLateDemandes() {
        demandeReparationService.sendReminderForLateDemandes();
    }

    @Scheduled(cron = "0 0 1 1/2 * ?") // This cron expression represents a task that runs at 00:00 on the 1st day of every 2 months
    public void checkRecurringDemandesReparation() {
        List<Object[]> counts = demandeReparationRepository.getTotalCountByActifReference();
        double average = demandeReparationService.getAverageCountByActifReference();

        for (Object[] count : counts) {
            String reference = (String) count[0];
            long countForReference = (Long) count[1];

            if (countForReference > average * 1.5) {
                Actif actif = actifRepository.findFirstByReference(reference);
                String message = "L'actif avec la référence " + reference + " et le modèle " + actif.getModele() + " de la marque " + actif.getMarque() + " est plus suceptible aux pannes récurrentes. "
                        + "Il a eu " + countForReference + " demandes de réparation, ce qui est 1.5 fois plus que la moyenne des demandes de réparation par actif. "
                        + "Nous vous recommandons de faire attention à ce modèle à l'avenir.";
                mailService.sendEmailToDSI(message);
            }
        }
    }
}