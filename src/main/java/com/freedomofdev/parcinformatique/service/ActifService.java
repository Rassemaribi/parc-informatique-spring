package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.enums.Etat;
import com.freedomofdev.parcinformatique.exception.BusinessException;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActifService {

    private final ActifRepository actifRepository;
    private final UserRepository userRepository;

    @Autowired
    public ActifService(ActifRepository actifRepository, UserRepository userRepository) {
        this.actifRepository = actifRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Actif> getAllActifs() {
        List<Actif> actifs = actifRepository.findAll();
        if (actifs == null || actifs.isEmpty()) {
            throw new BusinessException("Pas d'actifs trouvés");
        }
        return actifs;
    }

    @Transactional
    public Actif getActifById(Long id) {
        Optional<Actif> optionalActif = actifRepository.findById(id);
        return optionalActif.orElseThrow(() -> new BusinessException("Pas d'actif avec id: " + id));
    }

    @Transactional
    public List<Actif> createActifs(List<Actif> actifs, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec id: " + userId));

        actifs.forEach(actif -> actif.setCreatedByDSI(user));

        List<Actif> createdActifs = actifRepository.saveAll(actifs);
        if (createdActifs == null || createdActifs.isEmpty()) {
            throw new BusinessException("Problème lors de la création des actifs");
        }
        return createdActifs;
    }

    @Transactional
    public Actif updateActif(Actif updatedActif) {
        Actif existingActif = actifRepository.findById(updatedActif.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Actif", "id", updatedActif.getId()));

        // Copy properties from updatedActif to existingActif, excluding the user property
        BeanUtils.copyProperties(updatedActif, existingActif, "assignedUser");

        Actif savedActif = actifRepository.save(existingActif);
        if (savedActif == null) {
            throw new BusinessException("Problème lors de l'update de l'actif avec l'id: " + updatedActif.getId());
        }
        return savedActif;
    }

    @Transactional
    public Actif archiveActif(Long id) {
        Actif existingActif = actifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actif", "id", id));

        existingActif.setAssignedUser(null);
        existingActif.setEtat(Etat.ARCHIVE);

        Actif archivedActif = actifRepository.save(existingActif);
        if (archivedActif == null) {
            throw new BusinessException("Problème lors de l'archivage de l'actif: " + id);
        }
        return archivedActif;
    }
}