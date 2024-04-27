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

    Optional<User> findByNom(String nom);

    Optional<User> findByPrenom(String prenom);

    Optional<User> findByNumeroTelephone(String numeroTelephone);

    @EntityGraph(attributePaths = {"demandesAcquisitionCollaborateur", "demandesAcquisitionDSI", "demandesReparationCollaborateur", "demandesReparationDSI", "createdActifs", "assignedActifs", "roles"})
    Optional<User> findById(Long id);

}