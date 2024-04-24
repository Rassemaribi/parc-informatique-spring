package com.freedomofdev.parcinformatique.dto;

import com.freedomofdev.parcinformatique.entity.Actif;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private List<Actif> assignedActifs;
    private List<String> roles;
}