package com.freedomofdev.parcinformatique.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.freedomofdev.parcinformatique.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}