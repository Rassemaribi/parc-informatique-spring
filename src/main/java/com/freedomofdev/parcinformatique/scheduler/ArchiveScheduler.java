/*
package com.freedomofdev.parcinformatique.scheduler;

import com.freedomofdev.parcinformatique.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class ArchiveScheduler {
    @Autowired
    private ArchiveService archiveService;

    @Scheduled(cron = "0 * * * * ?") // This will execute the task every minute
    public void archiveDemandesReparationTest() {
        Date sevenDaysAgo = getSevenDaysAgo();
        archiveService.archiveDemandesReparation(sevenDaysAgo);
    }

    @Scheduled(cron = "0 * * * * ?") // This will execute the task every minute
    public void archiveDemandesAcquisitionTest() {
        Date sevenDaysAgo = getSevenDaysAgo();
        archiveService.archiveDemandesAcquisition(sevenDaysAgo);
    }

    private Date getSevenDaysAgo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7); // This will return a date 7 days ago
        return cal.getTime();
    }
}
 */