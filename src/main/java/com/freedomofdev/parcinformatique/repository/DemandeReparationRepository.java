package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DemandeReparationRepository extends JpaRepository<DemandeReparation, Long> {
    @EntityGraph(attributePaths = {"reparationHandledBy", "reparationRequestedBy", "actif", "actif.demandesReparation"})
    List<DemandeReparation> findByReparationRequestedBy_Id(Long userId);

    List<DemandeReparation> findByActifAndActive(Actif actif, boolean active);

    @Query("SELECT COUNT(dr) FROM demandes_reparation dr GROUP BY dr.actif.reference")
    List<Object[]> getCountsByActifReference();

    @Query("SELECT dr.actif.reference, COUNT(dr) FROM demandes_reparation dr GROUP BY dr.actif.reference")
    List<Object[]> getTotalCountByActifReference();

    List<DemandeReparation> findAllByDateRequestBefore(Date date);
}