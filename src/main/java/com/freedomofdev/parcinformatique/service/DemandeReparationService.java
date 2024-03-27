package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeReparationService {
    @Autowired
    private DemandeReparationRepository demandeReparationRepository;

    @Autowired
    private ActifRepository actifRepository;
    public DemandeReparation createDemandeReparation(DemandeReparation demandeReparation) {
        Long actifId = demandeReparation.getActif().getId();
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new RuntimeException("Error: Actif is not found."));
        demandeReparation.setActif(actif);
        return demandeReparationRepository.save(demandeReparation);
    }

    public DemandeReparation updateDemandeReparation(DemandeReparation demandeReparation) {
        return demandeReparationRepository.save(demandeReparation);
    }

    public List<DemandeReparation> getAllDemandeReparations() {
        return demandeReparationRepository.findAll();
    }

    public List<DemandeReparation> getDemandesReparationByUserId(Long userId) {
        return demandeReparationRepository.findByReparationRequestedBy_Id(userId);
    }
}
