package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.*;
import com.freedomofdev.parcinformatique.enums.StatusDemande;
import com.freedomofdev.parcinformatique.listener.DemandeReparationEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EntityListeners;


import java.util.Calendar;
import java.util.Date;

@Entity(name = "demandes_reparation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(DemandeReparationEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DemandeReparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "demande_description")
    private String demandeDescription;

    @Column(name = "rejet_motif")
    private String motifRejet;

    @Column(name = "reference")
    private String reference;

    @Column(name = "estimation")
    private Integer estimation;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "actif_id")
    private Actif actif;

    @Column(name = "active")
    private Boolean active = false;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User reparationRequestedBy;

    @JsonIdentityReference(alwaysAsId = true)
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

    public void setStatus(StatusDemande status) {
        this.status = status;
        updateActiveStatus();
    }

    private void updateActiveStatus() {
        if (status == StatusDemande.CREATED || status == StatusDemande.PENDING) {
            active = true;
        } else if (status == StatusDemande.DONE || status == StatusDemande.REFUSE) {
            active = false;
        }
    }
}