# TP 21 : Architecture Micro-services avec WebClient

Ce projet met en place une architecture microservices avec Spring Cloud Eureka pour la découverte de services et WebClient pour la communication entre services.

##  Ce qui sera appris

- Mettre en place Eureka Server
- Enregistrer des microservices (Eureka Client)
- Utiliser application.yml (YAML)
- Appeler un service par son nom Eureka avec WebClient

##  Prérequis techniques

- Java 17+
- Maven
- MySQL local (port 3306) + utilisateur (ex: root)
- Postman ou curl

##  Structure du projet
<img width="465" height="681" alt="Capture d’écran 2026-01-11 à 12 31 49" src="https://github.com/user-attachments/assets/52e8add8-0467-4243-8320-100c95535f22" />





##  Installation et démarrage

### Étape 0 : Préparer l'environnement

**Vérifier MySQL :**
- MySQL doit être démarré
- Le compte (ex: root) doit pouvoir créer des bases de données
- Port par défaut : 3306

**Validation :** Une connexion MySQL réussit (Workbench/CLI)

### Étape 1 : Lancer Eureka Server

```bash
cd eureka-server
mvn spring-boot:run
```

**Validation :**
- Ouvrir http://localhost:8761
- La page Eureka doit s'afficher
- Section "Instances currently registered" doit être vide (normal au début)

### Étape 2 : Lancer service-client

**Important :** Eureka Server doit être déjà lancé

```bash
cd service-client
mvn spring-boot:run
```

**Validation :**
- Dashboard Eureka : http://localhost:8761
- Section "Instances currently registered" doit contenir **SERVICE-CLIENT**

**Test de l'API :**
```bash
# Créer un client
curl -X POST http://localhost:8081/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom": "Ahmed", "age": 30}'

# Récupérer tous les clients
curl http://localhost:8081/api/clients
```

### Étape 3 : Lancer service-car

**Important :** Eureka Server et service-client doivent être déjà lancés

```bash
cd service-car
mvn spring-boot:run
```

**Validation :**
- Dashboard Eureka : http://localhost:8761
- Section "Instances currently registered" doit contenir :
  - **SERVICE-CLIENT**
  - **SERVICE-CAR**

**Test de WebClient :**
```bash
# Tester l'appel au service-client via WebClient
# (Remplacez 1 par l'ID d'un client créé précédemment)
curl http://localhost:8082/api/test/client/1
```

##  Tests end-to-end (scénario complet)

### Ordre de lancement
1.  eureka-server (8761)
2.  service-client (8081)
3.  service-car (8082)

### Scénario de test

**1. Créer un client :**
```bash
curl -X POST http://localhost:8081/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom": "Salma", "age": 22}'
```

**Réponse attendue :**
```json
{
  "id": 1,
  "nom": "Salma",
  "age": 22.0
}
```

**2. Récupérer tous les clients :**
```bash
curl http://localhost:8081/api/clients
```

**Noter l'ID du client créé (ex: 1)**

**3. Créer une voiture liée au client :**
```bash
curl -X POST http://localhost:8082/api/cars \
  -H "Content-Type: application/json" \
  -d '{"marque": "Toyota", "modele": "Yaris", "clientId": 1}'
```

**4. Lire les voitures enrichies (avec données client) :**
```bash
curl http://localhost:8082/api/cars
```

**Réponse attendue :**
```json
[
  {
    "id": 1,
    "marque": "Toyota",
    "modele": "Yaris",
    "clientId": 1,
    "client": {
      "id": 1,
      "nom": "Salma",
      "age": 22.0
    }
  }
]
```

**5. Récupérer les voitures d'un client spécifique :**
```bash
curl http://localhost:8082/api/cars/byClient/1
```

##  Dépannage (erreurs fréquentes)

### 1. "No instances available for SERVICE-CLIENT"

**Causes possibles :**
- `@LoadBalanced` absent sur `WebClient.Builder` dans `WebClientConfig`
- Dépendance `spring-cloud-starter-loadbalancer` absente dans `pom.xml`
- `service-client` non enregistré dans Eureka (vérifier dashboard)

**Solution :**
- Vérifier que `@LoadBalanced` est présent dans `WebClientConfig.java`
- Vérifier que `spring-cloud-starter-loadbalancer` est dans `pom.xml` de service-car
- Vérifier que service-client est visible dans http://localhost:8761

### 2. Service visible dans Eureka mais WebClient échoue

**Causes possibles :**
- Endpoint incorrect (vérifier `/api/clients/{id}`)
- Service-client démarré mais crash (vérifier les logs)
- Port incorrect dans l'URL

**Solution :**
- Vérifier les logs de service-client pour des erreurs
- Tester directement l'endpoint : `curl http://localhost:8081/api/clients/1`

### 3. Problème MySQL au démarrage

**Causes possibles :**
- MySQL arrêté
- Mauvais mot de passe
- Base non créée et droits insuffisants

**Solution :**
- Démarrer MySQL : `mysql.server start` (macOS) ou `sudo systemctl start mysql` (Linux)
- Vérifier le mot de passe dans `application.yml`
- Vérifier les droits de l'utilisateur MySQL

### 4. 404 sur les endpoints

**Causes possibles :**
- Chemin controller incorrect (`@RequestMapping`)
- Erreur de port (8081 vs 8082)
- Service non démarré

**Solution :**
- Vérifier le port dans `application.yml`
- Vérifier `@RequestMapping` dans le controller
- Vérifier que le service est bien démarré

##  Notes importantes

### Pourquoi `block()` est accepté ici ?
- Approche simple pour comprendre le mécanisme (TP débutant)
- En production, mieux vaut utiliser la programmation réactive complète (Mono/Flux sans block)

### Pourquoi éviter les relations JPA inter-services ?
- Deux bases de données différentes → JPA ne peut pas faire de join réel entre DB séparées
- Pattern correct en microservices : `id` + appel HTTP

##  Bases de données créées automatiquement

- **clientservicedb** : Base de données pour service-client
- **carservicedb** : Base de données pour service-car

Les tables sont créées automatiquement grâce à `ddl-auto: update` dans `application.yml`

##  Configuration MySQL

Si votre configuration MySQL est différente, modifiez dans `application.yml` de chaque service :
```yaml
spring:
  datasource:
    username: root        # Votre utilisateur MySQL
    password:             # Votre mot de passe MySQL
```

##  Points clés du TP

1. **Eureka Server** : Registre centralisé des services
2. **Eureka Client** : Enregistrement automatique des services
3. **WebClient** : Communication HTTP réactive entre services
4. **@LoadBalanced** : Résolution des noms de services Eureka
5. **Pattern d'enrichissement** : Agrégation de données depuis plusieurs services

---


