package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.enums.Etat;
import com.freedomofdev.parcinformatique.enums.StatusDemande;
import com.freedomofdev.parcinformatique.exception.BusinessException;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DemandeReparationService {
    private final DemandeReparationRepository demandeReparationRepository;
    private final ActifRepository actifRepository;
    private final UserRepository userRepository;

    @Autowired
    private MailService mailService;

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

        // Check if there is an active DemandeReparation for this Actif
        List<DemandeReparation> activeDemandes = demandeReparationRepository.findByActifAndActive(actif, true);
        if (!activeDemandes.isEmpty()) {
            throw new BusinessException("Cet actif a déja une demande de réparation active");
        }

        demandeReparation.setStatus(StatusDemande.CREATED);

        DemandeReparation createdDemandeReparation = demandeReparationRepository.saveAndFlush(demandeReparation);
        if (createdDemandeReparation == null) {
            throw new BusinessException("Problème lors de la création de la demande");
        }
        return createdDemandeReparation;
    }

    public DemandeReparation acceptDemandeReparation(Long id, Long userId) {
        return demandeReparationRepository.findById(id)
                .map(existingDemandeReparation -> {
                    existingDemandeReparation.setStatus(StatusDemande.PENDING);
                    existingDemandeReparation.setDateResponse(new Date());

                    // Fetch the User from the UserRepository using the provided userId
                    User handledByUser = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                    System.out.println("handledByUser: " + handledByUser);

                    existingDemandeReparation.setReparationHandledBy(handledByUser);

                    // Set the status of the Actif to EN_REPARATION
                    Actif actif = existingDemandeReparation.getActif();
                    actif.setEtat(Etat.EN_REPARATION);
                    actifRepository.save(actif);

                    DemandeReparation updatedDemandeReparation = demandeReparationRepository.save(existingDemandeReparation);
                    if (updatedDemandeReparation == null) {
                        throw new BusinessException("Problème lors de l'acceptation de la demande d'id: " + id);
                    }

                    // Send acceptance email
                    mailService.sendAcceptanceEmail(updatedDemandeReparation.getReparationRequestedBy(), updatedDemandeReparation);

                    return updatedDemandeReparation;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeReparation", "id", id));
    }

    public DemandeReparation rejectDemandeReparation(Long id, String rejetMotif, Long userId) {
        return demandeReparationRepository.findById(id)
                .map(existingDemandeReparation -> {
                    existingDemandeReparation.setStatus(StatusDemande.REFUSE);
                    existingDemandeReparation.setDateResponse(new Date());
                    existingDemandeReparation.setMotifRejet(rejetMotif);

                    // Fetch the User from the UserRepository using the provided userId
                    User handledByUser = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                    System.out.println("handledByUser: " + handledByUser);

                    existingDemandeReparation.setReparationHandledBy(handledByUser);
                    DemandeReparation updatedDemandeReparation = demandeReparationRepository.save(existingDemandeReparation);
                    if (updatedDemandeReparation == null) {
                        throw new BusinessException("Problème lors du rejet de la demande  d'id: " + id);
                    }

                    // Send rejection email
                    mailService.sendRejectionEmail(updatedDemandeReparation.getReparationRequestedBy(), updatedDemandeReparation);

                    return updatedDemandeReparation;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeReparation", "id", id));
    }

    // DemandeReparationService.java

    public DemandeReparation finirReparationAvecSucces(Long id) {
        return demandeReparationRepository.findById(id)
                .map(existingDemandeReparation -> {
                    if (existingDemandeReparation.getStatus() != StatusDemande.PENDING) {
                        throw new BusinessException("La demande de réparation n'est pas en cours");
                    }

                    existingDemandeReparation.setStatus(StatusDemande.DONE);

                    // Set the status of the Actif to ASSIGNED
                    Actif actif = existingDemandeReparation.getActif();
                    actif.setEtat(Etat.ASSIGNED);
                    actifRepository.save(actif);

                    DemandeReparation updatedDemandeReparation = demandeReparationRepository.save(existingDemandeReparation);
                    if (updatedDemandeReparation == null) {
                        throw new BusinessException("Problème lors de la finition de la demande de réparation d'id: " + id);
                    }

                    // Send repair success email
                    mailService.sendRepairSuccessEmail(updatedDemandeReparation.getReparationRequestedBy(), updatedDemandeReparation);

                    return updatedDemandeReparation;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeReparation", "id", id));
    }

    // DemandeReparationService.java

    public DemandeReparation finirReparationAvecEchec(Long id, Boolean archive) {
        return demandeReparationRepository.findById(id)
                .map(existingDemandeReparation -> {
                    if (existingDemandeReparation.getStatus() != StatusDemande.PENDING) {
                        throw new BusinessException("La demande de réparation n'est pas en cours");
                    }

                    existingDemandeReparation.setStatus(StatusDemande.DONE);

                    // Set the status of the Actif to EN_REBUT or ARCHIVE based on the archive parameter, and assigned user to null
                    Actif actif = existingDemandeReparation.getActif();
                    actif.setEtat(archive ? Etat.ARCHIVE : Etat.EN_REBUT);
                    actif.setAssignedUser(null);
                    actifRepository.save(actif);

                    DemandeReparation updatedDemandeReparation = demandeReparationRepository.save(existingDemandeReparation);
                    if (updatedDemandeReparation == null) {
                        throw new BusinessException("Problème lors de la finition de la demande de réparation d'id: " + id);
                    }

                    // Send repair failure email
                    mailService.sendRepairFailureEmail(updatedDemandeReparation.getReparationRequestedBy(), updatedDemandeReparation);

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

    public DemandeReparation getDemandeReparationById(Long id) {
        return demandeReparationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DemandeReparation", "id", id));
    }
}