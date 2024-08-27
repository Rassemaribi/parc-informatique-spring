package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeReparationArchive;
import com.freedomofdev.parcinformatique.repository.DemandeReparationArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class DemandeReparationArchiveService {

    @Autowired
    private DemandeReparationArchiveRepository repository;

    @Transactional(readOnly = true)
    public List<DemandeReparationArchive> getAll() {
        return repository.findAll();
    }
}