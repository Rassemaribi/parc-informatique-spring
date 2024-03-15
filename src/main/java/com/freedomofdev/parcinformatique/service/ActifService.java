package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActifService {

    private final ActifRepository actifRepository;

    @Autowired
    public ActifService(ActifRepository actifRepository) {
        this.actifRepository = actifRepository;
    }

    @Transactional(readOnly = true)
    public List<Actif> getAllActifs() {
        return actifRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Actif getActifById(Long id) {
        Optional<Actif> optionalActif = actifRepository.findById(id);
        return optionalActif.orElse(null);
    }

    @Transactional
    public Actif createActif(Actif actif) {
        return actifRepository.save(actif);
    }

    @Transactional
    public Actif updateActif(Actif actif) {
        return actifRepository.save(actif);
    }

    @Transactional
    public void deleteActif(Long id) {
        actifRepository.deleteById(id);
    }
}
