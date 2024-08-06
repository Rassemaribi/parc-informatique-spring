package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}