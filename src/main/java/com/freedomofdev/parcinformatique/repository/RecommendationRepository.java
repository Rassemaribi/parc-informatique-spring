package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}