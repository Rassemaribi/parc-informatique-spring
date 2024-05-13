package com.freedomofdev.parcinformatique.listener;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;

import jakarta.persistence.PostPersist;

import java.util.Calendar;

public class DemandeAcquisitionEntityListener {

    @PostPersist
    public void postPersist(DemandeAcquisition demandeAcquisition) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String reference = "ACQ" + year + "-" + demandeAcquisition.getId();
        demandeAcquisition.setReference(reference);
    }
}