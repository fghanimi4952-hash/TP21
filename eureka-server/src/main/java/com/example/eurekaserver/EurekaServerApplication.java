package com.example.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Application principale du serveur Eureka
 * 
 * @EnableEurekaServer : Active le rôle "serveur de découverte"
 * - Sans cette annotation : pas de dashboard, pas de registre
 * - Avec cette annotation : Eureka devient un serveur de registre pour les microservices
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
