package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.freedomofdev.parcinformatique.enums.Etat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@Entity(name = "actifs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String reference;
    private String numeroSerie;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    private String marque;
    private String modele;
    private String commentaires;
    private Etat etat;
    private String garantie;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_achat")
    private Date dateAchat;
    private boolean partage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "dsi_id")
    private User createdByDSI;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "actif", cascade = CascadeType.ALL)
    private List<DemandeReparation> demandesReparation = new ArrayList<>();
}