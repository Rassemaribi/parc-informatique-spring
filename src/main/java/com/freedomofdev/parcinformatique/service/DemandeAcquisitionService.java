package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.repository.DemandeAcquisitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeAcquisitionService {
    @Autowired
    private DemandeAcquisitionRepository demandeAcquisitionRepository;

    public DemandeAcquisition createDemandeAcquisition(DemandeAcquisition demandeAcquisition) {
        return demandeAcquisitionRepository.save(demandeAcquisition);
    }

    public DemandeAcquisition updateDemandeAcquisition(DemandeAcquisition demandeAcquisition) {
        return demandeAcquisitionRepository.save(demandeAcquisition);
    }

    public List<DemandeAcquisition> getAllDemandeAcquisitions() {
        return demandeAcquisitionRepository.findAll();
    }
}
