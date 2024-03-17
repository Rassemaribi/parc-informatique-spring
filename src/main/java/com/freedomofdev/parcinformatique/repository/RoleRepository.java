package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.Role;
import com.freedomofdev.parcinformatique.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(AppRole name);
}
