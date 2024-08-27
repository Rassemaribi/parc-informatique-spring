package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.DemandeReparationArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeReparationArchiveRepository extends JpaRepository<DemandeReparationArchive, Long> {
    List<DemandeReparationArchive> findAll();
}