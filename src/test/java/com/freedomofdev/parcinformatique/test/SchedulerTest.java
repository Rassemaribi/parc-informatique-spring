package com.freedomofdev.parcinformatique.test;

import com.freedomofdev.parcinformatique.scheduler.Scheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SchedulerTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void testCheckRecurringDemandesReparation() {
        scheduler.checkRecurringDemandesReparation();
    }
}