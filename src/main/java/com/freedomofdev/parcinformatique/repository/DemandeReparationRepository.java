package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeReparationRepository extends JpaRepository<DemandeReparation, Long> {
}