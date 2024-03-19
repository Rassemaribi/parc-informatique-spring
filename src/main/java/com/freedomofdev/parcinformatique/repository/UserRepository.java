package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"demandesAcquisitionCollaborateur", "demandesAcquisitionDSI", "demandesReparationCollaborateur", "demandesReparationDSI"})
    Optional<User> findById(Long id);
}