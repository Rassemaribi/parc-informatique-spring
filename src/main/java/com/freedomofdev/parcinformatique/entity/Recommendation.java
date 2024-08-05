package com.freedomofdev.parcinformatique.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Recommendation {





    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL)
    private List<ProductRecommendation> productRecommendations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "input_criteria_id", referencedColumnName = "id")
    private InputPayload inputCriteria;

    public void setInputCriteria(InputPayload inputCriteria) {
        if (inputCriteria == null) {
            if (this.inputCriteria != null) {
                this.inputCriteria.setRecommendation(null);
            }
        } else {
            inputCriteria.setRecommendation(this);
        }
        this.inputCriteria = inputCriteria;
    }




    // Constructors, getters, setters
}