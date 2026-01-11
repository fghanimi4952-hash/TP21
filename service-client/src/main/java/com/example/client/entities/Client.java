package com.example.client.entities;

import jakarta.persistence.*;

/**
 * Entité Client représentant un client dans la base de données
 * 
 * @Entity : Mappe cette classe vers une table MySQL
 * - Hibernate crée automatiquement la table "client" (nom par défaut)
 */
@Entity
public class Client {

    /**
     * Identifiant unique du client (auto-généré par MySQL)
     * @Id : Marque ce champ comme clé primaire
     * @GeneratedValue(strategy = GenerationType.IDENTITY) : Auto-incrément MySQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du client
     */
    private String nom;

    /**
     * Âge du client
     */
    private Float age;

    /**
     * Constructeur vide obligatoire pour JPA/Hibernate
     * Hibernate instancie l'objet par réflexion et utilise ce constructeur
     */
    public Client() {
    }

    /**
     * Constructeur avec paramètres
     * @param id Identifiant
     * @param nom Nom du client
     * @param age Âge du client
     */
    public Client(Long id, String nom, Float age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

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
