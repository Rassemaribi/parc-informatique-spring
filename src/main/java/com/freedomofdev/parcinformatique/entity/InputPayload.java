package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class InputPayload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("min_processor")
    private String minProcessor;
    @JsonProperty("min_ram")
    private String minRam;
    @JsonProperty("min_storage")
    private String minStorage;
    @JsonProperty("min_display_in_inch")
    private double minDisplayInInch;
    @JsonProperty("predicted_review")
    private double predictedReview;

    @OneToOne(mappedBy = "inputCriteria", cascade = CascadeType.ALL)
    private Recommendation recommendation;


    public InputPayload() {}

    // Getters and Setters
    public String getMinProcessor() {
        return minProcessor;
    }

    public void setMinProcessor(String minProcessor) {
        this.minProcessor = minProcessor;
    }

    public String getMinRam() {
        return minRam;
    }

    public void setMinRam(String minRam) {
        this.minRam = minRam;
    }

    public String getMinStorage() {
        return minStorage;
    }

    public void setMinStorage(String minStorage) {
        this.minStorage = minStorage;
    }

    public double getMinDisplayInInch() {
        return minDisplayInInch;
    }

    public void setMinDisplayInInch(double minDisplayInInch) {
        this.minDisplayInInch = minDisplayInInch;
    }

    public double getPredictedReview() {
        return predictedReview;
    }

    public void setPredictedReview(double predictedReview) {
        this.predictedReview = predictedReview;
    }



    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }
}