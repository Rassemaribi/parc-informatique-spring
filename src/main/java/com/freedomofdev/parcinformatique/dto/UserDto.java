package com.freedomofdev.parcinformatique.dto;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private List<Actif> assignedActifs;
    private List<Actif> createdActifs;
    private List<DemandeAcquisition> demandesAcquisitionCollaborateur;
    private List<DemandeAcquisition> demandesAcquisitionDSI;
    private List<DemandeReparation> demandesReparationCollaborateur;
    private List<DemandeReparation> demandesReparationDSI;
    private List<String> userGroups;
}