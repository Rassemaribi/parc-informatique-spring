package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeAcquisitionRepository extends JpaRepository<DemandeAcquisition, Long> {
}