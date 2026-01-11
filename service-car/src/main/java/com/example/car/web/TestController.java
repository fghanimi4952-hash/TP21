package com.example.car.web;

import com.example.car.entities.Client;
import com.example.car.services.ClientApi;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de test pour valider le fonctionnement de WebClient
 * 
 * Ce controller permet de tester l'appel au service-client
 * avant d'utiliser la fonctionnalité complète dans CarController
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    private final ClientApi clientApi;

    /**
     * Injection du service ClientApi via le constructeur
     * @param clientApi Service pour appeler le microservice service-client
     */
    public TestController(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    /**
     * Endpoint de test pour valider WebClient
     * GET /api/test/client/{id}
     * @param id Identifiant du client à récupérer
     * @return Le client correspondant depuis service-client
     * 
     * Usage: Tester que WebClient fonctionne correctement avec Eureka
     */
    @GetMapping("/client/{id}")
    public Client testClient(@PathVariable Long id) {
        return clientApi.findClientById(id);
    }
}
