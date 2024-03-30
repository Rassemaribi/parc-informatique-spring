package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.exception.BusinessException;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.DemandeAcquisitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DemandeAcquisitionService {

    private DemandeAcquisitionRepository demandeAcquisitionRepository;

    @Autowired
    public DemandeAcquisitionService(DemandeAcquisitionRepository demandeAcquisitionRepository) {
        this.demandeAcquisitionRepository = demandeAcquisitionRepository;
    }

    @Transactional
    public DemandeAcquisition createDemandeAcquisition(DemandeAcquisition demandeAcquisition) {
        DemandeAcquisition createdDemandeAcquisition = demandeAcquisitionRepository.save(demandeAcquisition);
        if (createdDemandeAcquisition == null) {
            throw new BusinessException("Problème lors de la création de la demande");
        }
        return createdDemandeAcquisition;
    }

    @Transactional
    public DemandeAcquisition updateDemandeAcquisition(Long id, DemandeAcquisition demandeAcquisition) {
        if (!demandeAcquisitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("DemandeAcquisition", "id", id);
        }
        demandeAcquisition.setId(id);
        DemandeAcquisition updatedDemandeAcquisition = demandeAcquisitionRepository.save(demandeAcquisition);
        if (updatedDemandeAcquisition == null) {
            throw new BusinessException("Problème lors de l'update de la demande d'acquisition d'id: " + id);
        }
        return updatedDemandeAcquisition;
    }

    @Transactional(readOnly = true)
    public List<DemandeAcquisition> getAllDemandeAcquisitions() {
        List<DemandeAcquisition> demandeAcquisitions = demandeAcquisitionRepository.findAll();
        if (demandeAcquisitions == null || demandeAcquisitions.isEmpty()) {
            throw new BusinessException("Pas de demandes d'acquisition trouvées");
        }
        return demandeAcquisitions;
    }
}