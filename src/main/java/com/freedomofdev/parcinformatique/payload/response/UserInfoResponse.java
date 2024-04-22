package com.freedomofdev.parcinformatique.payload.response;

import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private List<String> roles;

    public UserInfoResponse(Long id, String username, String email, String nom, String prenom, String numeroTelephone, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public List<String> getRoles() {
        return roles;
    }
}