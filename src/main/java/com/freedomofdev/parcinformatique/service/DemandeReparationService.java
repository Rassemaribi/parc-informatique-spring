package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.exception.BusinessException;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeReparationService {
    private final DemandeReparationRepository demandeReparationRepository;
    private final ActifRepository actifRepository;
    private final UserRepository userRepository;

    @Autowired
    public DemandeReparationService(DemandeReparationRepository demandeReparationRepository,
                                    ActifRepository actifRepository,
                                    UserRepository userRepository) {
        this.demandeReparationRepository = demandeReparationRepository;
        this.actifRepository = actifRepository;
        this.userRepository = userRepository;
    }

    public DemandeReparation createDemandeReparation(DemandeReparation demandeReparation) {
        Long actifId = demandeReparation.getActif().getId();
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new ResourceNotFoundException("Actif", "id", actifId));
        demandeReparation.setActif(actif);
        DemandeReparation createdDemandeReparation = demandeReparationRepository.save(demandeReparation);
        if (createdDemandeReparation == null) {
            throw new BusinessException("Problème lors de la création de la demande");
        }
        return createdDemandeReparation;
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
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                    System.out.println("handledByUser: " + handledByUser);

                    existingDemandeReparation.setReparationHandledBy(handledByUser);
                    DemandeReparation updatedDemandeReparation = demandeReparationRepository.save(existingDemandeReparation);
                    if (updatedDemandeReparation == null) {
                        throw new BusinessException("Problème lors de l'update de la demande avec l'id: " + id);
                    }
                    return updatedDemandeReparation;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeReparation", "id", id));
    }

    public List<DemandeReparation> getAllDemandeReparations() {
        List<DemandeReparation> demandeReparations = demandeReparationRepository.findAll();
        if (demandeReparations == null || demandeReparations.isEmpty()) {
            throw new BusinessException("Il n'y a pas de demandes de réparation trouvées");
        }
        return demandeReparations;
    }

    public List<DemandeReparation> getDemandesReparationByUserId(Long userId) {
        List<DemandeReparation> demandesReparation = demandeReparationRepository.findByReparationRequestedBy_Id(userId);
        if (demandesReparation == null || demandesReparation.isEmpty()) {
            throw new BusinessException("Pas de demandes de réparation pour cet utilisateur d'id: " + userId);
        }
        return demandesReparation;
    }
}