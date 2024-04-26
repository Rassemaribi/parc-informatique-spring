package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeReparationRepository extends JpaRepository<DemandeReparation, Long> {
    @EntityGraph(attributePaths = {"reparationHandledBy"})
    List<DemandeReparation> findByReparationRequestedBy_Id(Long userId);

    List<DemandeReparation> findByActifAndActive(Actif actif, boolean active);
}