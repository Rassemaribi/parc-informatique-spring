package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByActiveTrue();

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {
            "demandesAcquisitionCollaborateur",
            "demandesAcquisitionDSI",
            "demandesReparationCollaborateur",
            "demandesReparationDSI",
            "createdActifs",
            "createdActifs.demandesReparation", // Eagerly fetch the demandesReparation collection of the createdActifs
            "assignedActifs",
            "assignedActifs.demandesReparation", // Eagerly fetch the demandesReparation collection of the assignedActifs
            "userGroups"
    })
    Optional<User> findById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.id IN :ids")
    void deactivateUsers(@Param("ids") List<Long> ids);

}