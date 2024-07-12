package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(max = 50)
    @Email
    private String email;


    @Size(max = 50)
    private String nom;


    @Size(max = 50)
    private String prenom;


    @Size(max = 15)
    private String numeroTelephone;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "assignedUser")
    private List<Actif> assignedActifs = new ArrayList<>();

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "createdByDSI")
    private List<Actif> createdActifs = new ArrayList<>() ;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "acquisitionRequestedBy")
    private List<DemandeAcquisition> demandesAcquisitionCollaborateur = new ArrayList<>();

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "acquisitionHandledBy")
    private List<DemandeAcquisition> demandesAcquisitionDSI = new ArrayList<>();

    @OneToMany(mappedBy = "reparationRequestedBy")
    private List<DemandeReparation> demandesReparationCollaborateur;

    @OneToMany(mappedBy = "reparationHandledBy")
    private List<DemandeReparation> demandesReparationDSI;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> userGroups;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                '}';
    }
}