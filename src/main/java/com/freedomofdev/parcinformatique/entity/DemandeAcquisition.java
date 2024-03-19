package com.freedomofdev.parcinformatique.entity;

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

    @ManyToOne
    @JoinColumn(name = "actif_id")
    private Actif actif;

    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "responded_by_user_id")
    private User respondedBy;

    @Column(name = "request_reason")
    private String requestReason;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CREATED, ACCEPTED, REJECTED
    }
}