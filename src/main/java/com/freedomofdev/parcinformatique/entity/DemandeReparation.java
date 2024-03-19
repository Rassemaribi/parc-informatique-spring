package com.freedomofdev.parcinformatique.entity;

import com.freedomofdev.parcinformatique.enums.StatusDemande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "demande_reparation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeReparation {
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

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request")
    private Date dateRequest;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_response")
    private Date dateResponse;

    @Enumerated(EnumType.STRING)
    private StatusDemande status;
}