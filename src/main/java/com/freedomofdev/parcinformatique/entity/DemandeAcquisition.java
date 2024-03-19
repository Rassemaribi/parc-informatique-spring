package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference(value = "requestedByAcquisition-reference")
    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User acquisitionRequestedBy;

    @JsonBackReference(value = "handledByAcquisition-reference")
    @ManyToOne
    @JoinColumn(name = "responded_by_user_id")
    private User acquisitionHandledBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CREATED, ACCEPTED, REJECTED
    }
}