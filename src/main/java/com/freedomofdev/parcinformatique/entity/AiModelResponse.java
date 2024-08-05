package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AiModelResponse {
    @JsonProperty("input_criteria")
    private InputPayload input_criteria;
    @JsonProperty("recommended_laptops")
    private List<ProductRecommendation> recommended_laptops;

    // getters and setters

    public List<ProductRecommendation> getRecommendedLaptops() {
        return recommended_laptops;
    }

    public void setRecommendedLaptops(List<ProductRecommendation> recommended_laptops) {
        this.recommended_laptops = recommended_laptops;
    }
    public InputPayload getInputCriteria() {
        return input_criteria;
    }

    public void setInputCriteria(InputPayload input_criteria) {
        this.input_criteria = input_criteria;
    }
}