package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.freedomofdev.parcinformatique.enums.StatusDemande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "demande_reparations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeReparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "demande_description")
    private String demandeDescription;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "actif_id")
    private Actif actif;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "requested_by_user_id")
    private User requestedBy;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "responded_by_user_id")
    private User handledBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request")
    private Date dateRequest;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_response")
    private Date dateResponse;

    @Enumerated(EnumType.STRING)
    private StatusDemande status;
}