package com.example.client.web;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des clients
 * 
 * @RestController : Transforme automatiquement les réponses en JSON
 * @RequestMapping("/api/clients") : Base URL pour tous les endpoints de ce controller
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository repo;

    /**
     * Injection du repository via le constructeur (recommandé par Spring)
     * @param repo Repository pour accéder à la base de données
     */
    public ClientController(ClientRepository repo) {
        this.repo = repo;
    }

    /**
     * Récupère tous les clients
     * GET /api/clients
     * @return Liste de tous les clients
     */
    @GetMapping
    public List<Client> findAll() {
        return repo.findAll();
    }

    /**
     * Récupère un client par son ID
     * GET /api/clients/{id}
     * @param id Identifiant du client
     * @return Le client correspondant ou null si non trouvé
     */
    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Crée ou met à jour un client
     * POST /api/clients
     * @param c Client à sauvegarder (reçu en JSON dans le body de la requête)
     * @return Le client sauvegardé (avec ID généré si nouveau)
     * 
     * @RequestBody : Lit le JSON du body de la requête → convertit en objet Client
     */
    @PostMapping
    public Client save(@RequestBody Client c) {
        return repo.save(c);
    }
}
