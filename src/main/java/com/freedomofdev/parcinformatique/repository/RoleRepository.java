package com.freedomofdev.parcinformatique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.freedomofdev.parcinformatique.entity.Role;
import com.freedomofdev.parcinformatique.enums.AppRole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(AppRole name);
}
