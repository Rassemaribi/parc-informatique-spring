package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisitionArchive;
import com.freedomofdev.parcinformatique.repository.DemandeAcquisitionArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DemandeAcquisitionArchiveService {

    @Autowired
    private DemandeAcquisitionArchiveRepository repository;

    @Transactional(readOnly = true)
    public List<DemandeAcquisitionArchive> getAll() {
        return repository.findAll();
    }
}