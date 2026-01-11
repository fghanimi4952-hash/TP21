package com.example.client.repositories;

import com.example.client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface Repository pour l'entité Client
 * 
 * JpaRepository fournit directement :
 * - findAll() : récupère tous les clients
 * - findById(Long id) : récupère un client par son ID
 * - save(Client c) : sauvegarde ou met à jour un client
 * - deleteById(Long id) : supprime un client par son ID
 * - etc.
 * 
 * Pas besoin d'implémentation : Spring Data JPA la génère automatiquement
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
