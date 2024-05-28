package com.freedomofdev.parcinformatique.listener;

import com.freedomofdev.parcinformatique.entity.DemandeReparation;

import jakarta.persistence.PostPersist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemandeReparationEntityListener {

    @PostPersist
    public void postPersist(DemandeReparation demandeReparation) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String reference = "FOD-REP-" + date + "-" + demandeReparation.getId();
        demandeReparation.setReference(reference);
    }
}