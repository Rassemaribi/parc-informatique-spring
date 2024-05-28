package com.freedomofdev.parcinformatique.listener;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;

import jakarta.persistence.PostPersist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemandeAcquisitionEntityListener {

    @PostPersist
    public void postPersist(DemandeAcquisition demandeAcquisition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String reference = "FOD-ACQ-" + date + "-" + demandeAcquisition.getId();
        demandeAcquisition.setReference(reference);
    }
}