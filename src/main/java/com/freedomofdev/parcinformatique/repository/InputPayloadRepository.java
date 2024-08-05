package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.InputPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputPayloadRepository extends JpaRepository<InputPayload, Long> {

}