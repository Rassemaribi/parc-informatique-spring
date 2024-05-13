package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.freedomofdev.parcinformatique.listener.DemandeAcquisitionEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "demandes_acquisition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(DemandeAcquisitionEntityListener.class)
public class DemandeAcquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "demande_description")
    private String demandeDescription;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User acquisitionRequestedBy;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "responded_by_user_id")
    private User acquisitionHandledBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "reference")
    private String reference;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_request")
    private Date dateRequest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_response")
    private Date dateResponse;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CREATED, PENDING, DONE, REFUSE
    }
}