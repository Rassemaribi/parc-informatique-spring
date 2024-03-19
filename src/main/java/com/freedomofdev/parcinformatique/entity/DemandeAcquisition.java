package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "demande_acquisitions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeAcquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "demande_description")
    private String demandeDescription;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "requested_by_user_id")
    private User requestedBy;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "responded_by_user_id")
    private User handledBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CREATED, ACCEPTED, REJECTED
    }
}