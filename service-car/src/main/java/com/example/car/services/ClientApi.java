package com.example.car.services;

import com.example.car.entities.Client;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service pour appeler le microservice service-client via WebClient
 * 
 * @Service : Marque cette classe comme un service Spring
 */
@Service
public class ClientApi {
    
    private final WebClient.Builder builder;

    /**
     * Injection du WebClient.Builder via le constructeur
     * @param builder Builder pour créer des instances WebClient
     */
    public ClientApi(WebClient.Builder builder) {
        this.builder = builder;
    }

    /**
     * Récupère un client par son ID en appelant le service-client
     * 
     * @param id Identifiant du client
     * @return Le client correspondant ou null si non trouvé
     * 
     * Explication du flux :
     * 1. build() : Crée un WebClient concret
     * 2. get() : Prépare une requête HTTP GET
     * 3. uri(...) : URL logique par nom Eureka (résolu grâce à @LoadBalanced)
     * 4. retrieve() : Exécute l'appel HTTP + prépare la lecture de la réponse
     * 5. bodyToMono(Client.class) : Déserialise la réponse JSON en objet Client
     * 6. block() : Attend la réponse de manière bloquante (simple pour TP)
     *    Note: En production, mieux vaut utiliser la programmation réactive complète
     */
    public Client findClientById(Long id) {
        return builder.build()
                .get()
                .uri("http://SERVICE-CLIENT/api/clients/" + id)
                .retrieve()
                .bodyToMono(Client.class)
                .block();
    }
}
