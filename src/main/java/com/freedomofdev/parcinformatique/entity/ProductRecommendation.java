package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ram;
    private String processor;
    @JsonProperty("display_in_inch")
    private double displayInInch;
    private String storage;
    private double rating;
    @JsonProperty("predicted_price")
    private double predictedPrice;


    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "input_criteria_id", referencedColumnName = "id")
    private InputPayload inputCriteria;*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    @JsonIgnore
    private Recommendation recommendation;





    // Constructors, getters, setters
}