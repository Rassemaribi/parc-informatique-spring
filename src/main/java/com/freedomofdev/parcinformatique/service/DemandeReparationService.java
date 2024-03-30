package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.entity.User;
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

    @Autowired
    private UserRepository userRepository;

    public DemandeReparation createDemandeReparation(DemandeReparation demandeReparation) {
        Long actifId = demandeReparation.getActif().getId();
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new RuntimeException("Error: Actif is not found."));
        demandeReparation.setActif(actif);
        return demandeReparationRepository.save(demandeReparation);
    }

    public DemandeReparation updateDemandeReparation(Long id, DemandeReparation newDemandeReparation, Long userId) {
        return demandeReparationRepository.findById(id)
                .map(existingDemandeReparation -> {
                    existingDemandeReparation.setStatus(newDemandeReparation.getStatus());
                    existingDemandeReparation.setDateResponse(newDemandeReparation.getDateResponse());

                    // Check if motifRejet is present in newDemandeReparation
                    if (newDemandeReparation.getMotifRejet() != null) {
                        existingDemandeReparation.setMotifRejet(newDemandeReparation.getMotifRejet());
                    }

                    // Fetch the User from the UserRepository using the provided userId
                    User handledByUser = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("Error: User is not found."));
                    System.out.println("handledByUser: " + handledByUser);

                    existingDemandeReparation.setReparationHandledBy(handledByUser);
                    return demandeReparationRepository.save(existingDemandeReparation);
                })
                .orElseThrow(() -> new RuntimeException("Error: DemandeReparation is not found."));
    }

    public List<DemandeReparation> getAllDemandeReparations() {
        return demandeReparationRepository.findAll();
    }

    public List<DemandeReparation> getDemandesReparationByUserId(Long userId) {
        return demandeReparationRepository.findByReparationRequestedBy_Id(userId);
    }
}
