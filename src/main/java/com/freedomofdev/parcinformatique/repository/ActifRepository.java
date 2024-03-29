package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.Actif;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActifRepository extends JpaRepository<Actif, Long> {
    @EntityGraph(attributePaths = {"demandesReparation"})
    Optional<Actif> findById(Long id);
}
