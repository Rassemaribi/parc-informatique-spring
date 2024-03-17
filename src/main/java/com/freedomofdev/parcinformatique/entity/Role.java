package com.freedomofdev.parcinformatique.entity;

import com.freedomofdev.parcinformatique.enums.AppRole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppRole name;

    public Role() {

    }

    public Role(AppRole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppRole getName() {
        return name;
    }

    public void setName(AppRole name) {
        this.name = name;
    }
}