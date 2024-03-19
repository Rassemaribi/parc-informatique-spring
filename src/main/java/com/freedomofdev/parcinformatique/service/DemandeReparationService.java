package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeReparationService {
    @Autowired
    private DemandeReparationRepository demandeReparationRepository;

    public DemandeReparation createDemandeReparation(DemandeReparation demandeReparation) {
        return demandeReparationRepository.save(demandeReparation);
    }

    public DemandeReparation updateDemandeReparation(DemandeReparation demandeReparation) {
        return demandeReparationRepository.save(demandeReparation);
    }

    public List<DemandeReparation> getAllDemandeReparations() {
        return demandeReparationRepository.findAll();
    }
}
