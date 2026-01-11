package com.example.car.entities;

/**
 * DTO (Data Transfer Object) Client
 * 
 * Cette classe sert uniquement à "mapper" le JSON reçu du service-client
 * Ce n'est pas une entité JPA car elle ne représente pas une table dans la base de données
 * Elle est utilisée pour désérialiser la réponse HTTP du service-client
 */
public class Client {
    private Long id;
    private String nom;
    private Float age;

    /**
     * Constructeur vide
     */
    public Client() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }
}
