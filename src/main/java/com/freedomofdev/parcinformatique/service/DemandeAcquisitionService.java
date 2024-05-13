package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.exception.BusinessException;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.DemandeAcquisitionRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DemandeAcquisitionService {

    @Autowired
    private DemandeAcquisitionRepository demandeAcquisitionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Autowired
    public DemandeAcquisitionService(DemandeAcquisitionRepository demandeAcquisitionRepository, UserRepository userRepository) {
        this.demandeAcquisitionRepository = demandeAcquisitionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public DemandeAcquisition createDemandeAcquisition(DemandeAcquisition demandeAcquisition) {
        DemandeAcquisition createdDemandeAcquisition = demandeAcquisitionRepository.save(demandeAcquisition);
        if (createdDemandeAcquisition == null) {
            throw new BusinessException("Problème lors de la création de la demande");
        }
        return createdDemandeAcquisition;
    }

    // DemandeAcquisitionService.java

    @Transactional
    public DemandeAcquisition acceptDemandeAcquisition(Long id, Long userId) {
        return demandeAcquisitionRepository.findById(id)
                .map(existingDemandeAcquisition -> {
                    existingDemandeAcquisition.setStatus(DemandeAcquisition.Status.DONE);
                    existingDemandeAcquisition.setDateResponse(new Date());

                    User handledByUser = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                    existingDemandeAcquisition.setAcquisitionHandledBy(handledByUser);

                    DemandeAcquisition updatedDemandeAcquisition = demandeAcquisitionRepository.save(existingDemandeAcquisition);

                    // Send acceptance email
                    mailService.sendAcquisitionAcceptanceEmail(updatedDemandeAcquisition.getAcquisitionRequestedBy(), updatedDemandeAcquisition);

                    return updatedDemandeAcquisition;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeAcquisition", "id", id));
    }

    @Transactional
    public DemandeAcquisition rejectDemandeAcquisition(Long id, String rejetMotif, Long userId) {
        return demandeAcquisitionRepository.findById(id)
                .map(existingDemandeAcquisition -> {
                    existingDemandeAcquisition.setStatus(DemandeAcquisition.Status.REFUSE);
                    existingDemandeAcquisition.setDateResponse(new Date());
                    existingDemandeAcquisition.setRejectionReason(rejetMotif);

                    User handledByUser = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                    existingDemandeAcquisition.setAcquisitionHandledBy(handledByUser);

                    DemandeAcquisition updatedDemandeAcquisition = demandeAcquisitionRepository.save(existingDemandeAcquisition);

                    // Send rejection email
                    mailService.sendAcquisitionRejectionEmail(updatedDemandeAcquisition.getAcquisitionRequestedBy(), updatedDemandeAcquisition);

                    return updatedDemandeAcquisition;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeAcquisition", "id", id));
    }

    @Transactional
    public DemandeAcquisition notifyDemandeAcquisition(Long id) {
        return demandeAcquisitionRepository.findById(id)
                .map(existingDemandeAcquisition -> {
                    existingDemandeAcquisition.setStatus(DemandeAcquisition.Status.PENDING);
                    existingDemandeAcquisition.setDateResponse(new Date());

                    DemandeAcquisition updatedDemandeAcquisition = demandeAcquisitionRepository.save(existingDemandeAcquisition);

                    // Send notification email
                    mailService.sendAcquisitionNotificationEmail(updatedDemandeAcquisition.getAcquisitionRequestedBy(), updatedDemandeAcquisition);

                    return updatedDemandeAcquisition;
                })
                .orElseThrow(() -> new ResourceNotFoundException("DemandeAcquisition", "id", id));
    }

    @Transactional(readOnly = true)
    public List<DemandeAcquisition> getAllDemandeAcquisitions() {
        List<DemandeAcquisition> demandeAcquisitions = demandeAcquisitionRepository.findAll();
        if (demandeAcquisitions == null || demandeAcquisitions.isEmpty()) {
            throw new BusinessException("Pas de demandes d'acquisition trouvées");
        }
        return demandeAcquisitions;
    }

    @Transactional(readOnly = true)
    public List<DemandeAcquisition> getDemandesAcquisitionByUserId(Long userId) {
        return demandeAcquisitionRepository.findByAcquisitionRequestedBy_Id(userId);
    }

    @Transactional(readOnly = true)
    public DemandeAcquisition getDemandeAcquisitionById(Long id) {
        return demandeAcquisitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DemandeAcquisition", "id", id));
    }
}