package com.example.appdai.model;

import jakarta.validation.constraints.NotBlank;

/*
Le package model contient :

    Les classes représentant tes données (entités ou objets métiers).
    Les DTOs (Data Transfer Objects) pour transférer des données entre ton frontend et backend.
 */


// CEST UN EXEMPLE DE MODEL
public class Pc {
    private Long id;

    @NotBlank(message = "Card name cannot be blank")
    private String name;

    private String description;

    private String ownerUsername;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
