package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisitionArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeAcquisitionArchiveRepository extends JpaRepository<DemandeAcquisitionArchive, Long> {
    List<DemandeAcquisitionArchive> findAll();
}