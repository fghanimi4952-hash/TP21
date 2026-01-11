package com.example.car.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration de WebClient pour les appels HTTP entre microservices
 * 
 * @Configuration : Cette classe contient des configurations Spring
 */
@Configuration
public class WebClientConfig {

    /**
     * Crée un bean WebClient.Builder avec support du load balancing
     * 
     * @LoadBalanced : CRUCIAL - Permet d'utiliser le nom du service Eureka dans l'URL
     * - Sans @LoadBalanced : Spring ne peut pas résoudre "SERVICE-CLIENT"
     * - Avec @LoadBalanced : Spring résout "http://SERVICE-CLIENT/api/clients/1"
     *   vers l'instance réelle du service enregistré dans Eureka
     * 
     * @return WebClient.Builder configuré avec load balancing
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
