package com.example.car.entities;

import jakarta.persistence.*;

/**
 * Entité Car représentant une voiture dans la base de données
 */
@Entity
public class Car {
    
    /**
     * Identifiant unique de la voiture (auto-généré par MySQL)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Marque de la voiture (ex: Toyota, Renault)
     */
    private String marque;
    
    /**
     * Modèle de la voiture (ex: Yaris, Clio)
     */
    private String modele;

    /**
     * Référence vers le client (stockée localement)
     * Lien logique vers un client dans le microservice service-client
     * Note: Pas de relation JPA car les bases de données sont séparées en microservices
     */
    private Long clientId;

    /**
     * Client enrichi (non persisté dans la base de données)
     * @Transient : Hibernate ignore ce champ lors de la sauvegarde
     * Sert uniquement à enrichir la réponse JSON en récupérant les données du client
     * via un appel HTTP au service-client
     */
    @Transient
    private Client client;

    /**
     * Constructeur vide obligatoire pour JPA/Hibernate
     */
    public Car() {}

    /**
     * Constructeur avec paramètres
     * @param id Identifiant
     * @param marque Marque de la voiture
     * @param modele Modèle de la voiture
     * @param clientId ID du client propriétaire
     */
    public Car(Long id, String marque, String modele, Long clientId) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.clientId = clientId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
