package com.freedomofdev.parcinformatique.repository;

import com.freedomofdev.parcinformatique.entity.ProductRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, Long> {
}