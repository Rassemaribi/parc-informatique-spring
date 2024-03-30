package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.enums.Etat;
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
        return actifRepository.findAll();
    }

    @Transactional
    public Actif getActifById(Long id) {
        Optional<Actif> optionalActif = actifRepository.findById(id);
        return optionalActif.orElse(null);
    }

    @Transactional
    public List<Actif> createActifs(List<Actif> actifs, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        actifs.forEach(actif -> actif.setCreatedByDSI(user));

        return actifRepository.saveAll(actifs);
    }

    /* no longer used (all go to /batch)
    public Actif createActif(Actif actif, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        actif.setCreatedByDSI(user);

        return actifRepository.save(actif);
    }
    */

    @Transactional
    public Actif updateActif(Actif updatedActif) {
        Actif existingActif = actifRepository.findById(updatedActif.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Actif", "id", updatedActif.getId()));

        // Copy properties from updatedActif to existingActif, excluding the user property
        BeanUtils.copyProperties(updatedActif, existingActif, "assignedUser");

        return actifRepository.save(existingActif);
    }

    @Transactional
    public Actif archiveActif(Long id) {
        Actif existingActif = actifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actif", "id", id));

        existingActif.setAssignedUser(null); // Unassign the Actif from the user
        existingActif.setEtat(Etat.ARCHIVE);

        return actifRepository.save(existingActif);
    }
}
