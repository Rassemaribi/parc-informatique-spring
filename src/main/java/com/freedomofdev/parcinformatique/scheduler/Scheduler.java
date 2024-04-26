package com.freedomofdev.parcinformatique.scheduler;

import com.freedomofdev.parcinformatique.service.DemandeReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    @Autowired
    private DemandeReparationService demandeReparationService;

    @Scheduled(cron = "0 0 0 * * ?") // run every day at 00:00
    public void sendReminderForLateDemandes() {
        demandeReparationService.sendReminderForLateDemandes();
    }
}