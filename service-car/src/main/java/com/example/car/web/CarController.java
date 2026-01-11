package com.example.car.web;

import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;
import com.example.car.services.ClientApi;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des voitures avec enrichissement client
 * 
 * Pattern d'enrichissement utilisé :
 * 1. Lecture depuis la DB locale (carservicedb)
 * 2. Appel HTTP au service-client pour récupérer les données du client
 * 3. Enrichissement de la réponse JSON (car + client)
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository repo;
    private final ClientApi clientApi;

    /**
     * Injection des dépendances via le constructeur
     * @param repo Repository pour accéder à la base de données locale
     * @param clientApi Service pour appeler le microservice service-client
     */
    public CarController(CarRepository repo, ClientApi clientApi) {
        this.repo = repo;
        this.clientApi = clientApi;
    }

    /**
     * Crée une nouvelle voiture
     * POST /api/cars
     * @param car Voiture à sauvegarder (reçue en JSON)
     * @return La voiture sauvegardée avec son ID généré
     * 
     * Note: Sauvegarde uniquement la voiture dans la DB locale (carservicedb)
     * Le clientId doit référencer un client existant dans service-client
     */
    @PostMapping
    public Car save(@RequestBody Car car) {
        return repo.save(car);
    }

    /**
     * Récupère toutes les voitures avec enrichissement client
     * GET /api/cars
     * @return Liste de toutes les voitures avec leurs clients enrichis
     * 
     * Pattern d'enrichissement :
     * - Pour chaque voiture, on récupère le client correspondant via WebClient
     * - Le JSON final devient "agrégé" (car + client)
     */
    @GetMapping
    public List<Car> findAll() {
        // 1. Récupère toutes les voitures depuis la DB locale
        List<Car> cars = repo.findAll();

        // 2. Enrichissement : pour chaque voiture, récupérer le client
        for (Car car : cars) {
            if (car.getClientId() != null) {
                // Appel HTTP au service-client via WebClient
                car.setClient(clientApi.findClientById(car.getClientId()));
            }
        }
        
        return cars;
    }

    /**
     * Récupère toutes les voitures d'un client donné
     * GET /api/cars/byClient/{clientId}
     * @param clientId Identifiant du client
     * @return Liste des voitures du client avec les données du client enrichies
     * 
     * Note: Toutes les voitures partagent le même client (optimisation possible)
     */
    @GetMapping("/byClient/{clientId}")
    public List<Car> findByClient(@PathVariable Long clientId) {
        // 1. Récupère les voitures du client depuis la DB locale
        List<Car> cars = repo.findByClientId(clientId);

        // 2. Enrichissement : récupère le client une seule fois (même client pour toutes)
        for (Car car : cars) {
            car.setClient(clientApi.findClientById(clientId));
        }
        
        return cars;
    }
}
