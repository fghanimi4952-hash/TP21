package com.example.car.repositories;

import com.example.car.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface Repository pour l'entité Car
 * 
 * Méthode dérivée : findByClientId(Long clientId)
 * Spring Data JPA génère automatiquement la requête SQL :
 * SELECT * FROM car WHERE client_id = ?
 */
public interface CarRepository extends JpaRepository<Car, Long> {
    /**
     * Récupère toutes les voitures appartenant à un client donné
     * @param clientId Identifiant du client
     * @return Liste des voitures du client
     */
    List<Car> findByClientId(Long clientId);
}
