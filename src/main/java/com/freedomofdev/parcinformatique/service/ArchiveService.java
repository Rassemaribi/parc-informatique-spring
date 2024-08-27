package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.entity.DemandeAcquisitionArchive;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.entity.DemandeReparationArchive;
import com.freedomofdev.parcinformatique.repository.DemandeAcquisitionArchiveRepository;
import com.freedomofdev.parcinformatique.repository.DemandeAcquisitionRepository;
import com.freedomofdev.parcinformatique.repository.DemandeReparationArchiveRepository;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArchiveService {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveService.class);

    @Autowired
    private DemandeReparationRepository demandeReparationRepository;

    @Autowired
    private DemandeReparationArchiveRepository demandeReparationArchiveRepository;

    @Autowired
    private DemandeAcquisitionRepository demandeAcquisitionRepository;

    @Autowired
    private DemandeAcquisitionArchiveRepository demandeAcquisitionArchiveRepository;
    /*
        @Transactional
        public void archiveDemandesReparation(Date date) {
            List<DemandeReparation> demandesReparation = demandeReparationRepository.findAllByDateRequestBefore(date);

            List<DemandeReparationArchive> demandesReparationArchive = demandesReparation.stream()
                    .map(this::convertToArchive)
                    .collect(Collectors.toList());
            System.out.println(demandesReparation);
            System.out.println(demandesReparationArchive);
            demandeReparationArchiveRepository.saveAll(demandesReparationArchive);
            try {
                demandeReparationRepository.deleteAll(demandesReparation);
            } catch (Exception e) {
                logger.error("Error occurred while deleting entities", e);
            }
            System.out.println(demandesReparation);
        }
        @Transactional
        public void archiveDemandesAcquisition(Date date) {
            List<DemandeAcquisition> demandesAcquisition = demandeAcquisitionRepository.findAllByDateRequestBefore(date);

            List<DemandeAcquisitionArchive> demandesAcquisitionArchive = demandesAcquisition.stream()
                    .map(this::convertToArchive)
                    .collect(Collectors.toList());

            System.out.println(demandesAcquisitionArchive);
            System.out.println(demandesAcquisition);
            demandeAcquisitionArchiveRepository.saveAll(demandesAcquisitionArchive);
            demandeAcquisitionRepository.deleteAll(demandesAcquisition);
        }
    */
    @Transactional
    @Scheduled(cron = "0 0 0 1 1/3 ?")
    public void archiveDemandesReparation() {
        Date threeMonthsAgo = getThreeMonthsAgo();

        List<DemandeReparation> demandesReparation = demandeReparationRepository.findAllByDateRequestBefore(threeMonthsAgo);

        List<DemandeReparationArchive> demandesReparationArchive = demandesReparation.stream()
                .map(this::convertToArchive)
                .collect(Collectors.toList());

        demandeReparationArchiveRepository.saveAll(demandesReparationArchive);
        demandeReparationRepository.deleteAll(demandesReparation);
    }



    @Transactional
    @Scheduled(cron = "0 0 0 1 1/3 ?")
    public void archiveDemandesAcquisition() {
        Date threeMonthsAgo = getThreeMonthsAgo();

        List<DemandeAcquisition> demandesAcquisition = demandeAcquisitionRepository.findAllByDateRequestBefore(threeMonthsAgo);

        List<DemandeAcquisitionArchive> demandesAcquisitionArchive = demandesAcquisition.stream()
                .map(this::convertToArchive)
                .collect(Collectors.toList());

        demandeAcquisitionArchiveRepository.saveAll(demandesAcquisitionArchive);
        demandeAcquisitionRepository.deleteAll(demandesAcquisition);
    }

    private Date getThreeMonthsAgo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        return cal.getTime();
    }

    private DemandeReparationArchive convertToArchive(DemandeReparation demandeReparation) {
        DemandeReparationArchive archive = new DemandeReparationArchive();
        archive.setId(demandeReparation.getId());
        archive.setDemandeDescription(demandeReparation.getDemandeDescription());
        archive.setMotifRejet(demandeReparation.getMotifRejet());
        archive.setReference(demandeReparation.getReference());
        archive.setEstimation(demandeReparation.getEstimation());
        archive.setActif(demandeReparation.getActif());
        archive.setActive(demandeReparation.getActive());
        archive.setReparationRequestedBy(demandeReparation.getReparationRequestedBy());
        archive.setReparationHandledBy(demandeReparation.getReparationHandledBy());
        archive.setDateRequest(demandeReparation.getDateRequest());
        archive.setDateResponse(demandeReparation.getDateResponse());
        archive.setStatus(demandeReparation.getStatus());
        return archive;
    }

    private DemandeAcquisitionArchive convertToArchive(DemandeAcquisition demandeAcquisition) {
        DemandeAcquisitionArchive archive = new DemandeAcquisitionArchive();
        archive.setId(demandeAcquisition.getId());
        archive.setDemandeDescription(demandeAcquisition.getDemandeDescription());
        archive.setAcquisitionRequestedBy(demandeAcquisition.getAcquisitionRequestedBy());
        archive.setAcquisitionHandledBy(demandeAcquisition.getAcquisitionHandledBy());
        archive.setRejectionReason(demandeAcquisition.getRejectionReason());
        archive.setReference(demandeAcquisition.getReference());
        archive.setDateRequest(demandeAcquisition.getDateRequest());
        archive.setDateResponse(demandeAcquisition.getDateResponse());
        archive.setStatus(demandeAcquisition.getStatus());
        return archive;
    }
}