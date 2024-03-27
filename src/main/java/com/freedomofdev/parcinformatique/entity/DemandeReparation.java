package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.freedomofdev.parcinformatique.enums.StatusDemande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "demandes_reparation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeReparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "demande_description")
    private String demandeDescription;

    @JsonBackReference(value = "actif-reference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actif_id")
    private Actif actif;

    @JsonBackReference(value = "requestedByReparation-reference")
    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User reparationRequestedBy;

    @JsonBackReference(value = "handledByReparation-reference")
    @ManyToOne
    @JoinColumn(name = "responded_by_user_id")
    private User reparationHandledBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_request")
    private Date dateRequest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_response")
    private Date dateResponse;

    @Enumerated(EnumType.STRING)
    private StatusDemande status;
}