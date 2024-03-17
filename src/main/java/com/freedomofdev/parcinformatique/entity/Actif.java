package com.freedomofdev.parcinformatique.entity;

import com.freedomofdev.parcinformatique.enums.Etat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
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

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    private String marque;
    private String modele;
    private String caracteristiques;
    private Etat etat;
    private String garantie;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_achat")
    private Date dateAchat;
    private boolean partage;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}