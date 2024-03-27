package com.freedomofdev.parcinformatique.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @JsonIgnore
    @Size(max = 120)
    private String password;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL)
    private List<Actif> assignedActifs = new ArrayList<>();

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "createdByDSI", cascade = CascadeType.ALL)
    private List<Actif> createdActifs = new ArrayList<>();

    @JsonManagedReference(value = "requestedByAcquisition-reference")
    @OneToMany(mappedBy = "acquisitionRequestedBy")
    private List<DemandeAcquisition> demandesAcquisitionCollaborateur = new ArrayList<>();

    @JsonManagedReference(value = "handledByAcquisition-reference")
    @OneToMany(mappedBy = "acquisitionHandledBy")
    private List<DemandeAcquisition> demandesAcquisitionDSI = new ArrayList<>();

    @JsonManagedReference(value = "requestedByReparation-reference")
    @OneToMany(mappedBy = "reparationRequestedBy", fetch = FetchType.EAGER)
    private List<DemandeReparation> demandesReparationCollaborateur;

    @JsonManagedReference(value = "handledByReparation-reference")
    @OneToMany(mappedBy = "reparationHandledBy")
    private List<DemandeReparation> demandesReparationDSI;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}