package com.freedomofdev.parcinformatique.listener;

import com.freedomofdev.parcinformatique.entity.DemandeReparation;

import jakarta.persistence.PostPersist;

import java.util.Calendar;

public class DemandeReparationEntityListener {

    @PostPersist
    public void postPersist(DemandeReparation demandeReparation) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String reference = "REP" + year + "-" + demandeReparation.getId();
        demandeReparation.setReference(reference);
    }
}