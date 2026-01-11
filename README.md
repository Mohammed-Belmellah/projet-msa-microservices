# 📚 Projet MSA - Gestion de Bibliothèque (Microservices)

**Auteur :** Mohammed Belmellah
**Module :** Architectures Microservices
**Année :** 2026

---

## 📋 Description du Projet

Application distribuée de gestion de bibliothèque basée sur une architecture **Microservices**.
Ce projet met en œuvre les concepts clés des systèmes distribués : décomposition par domaine métier, bases de données isolées (**Database per Service**), communication synchrone (REST) et communication asynchrone événementielle (**Kafka**).

### ✅ Fonctionnalités principales
* Gestion des **Utilisateurs** (Adhérents).
* Gestion des **Livres**.
* Gestion des **Emprunts** (Vérification de l'utilisateur et du livre via OpenFeign).
* **Système de Notification Asynchrone :** Envoi d'une simulation d'email via Kafka lorsqu'un emprunt est validé.

---

## 🏗️ Architecture Technique

Le système est composé de **6 conteneurs Docker** orchestrés via Docker Compose :

### 1. Services d'Infrastructure
* **Discovery Service (Eureka)** : Registre de services (Port `8761`).
* **Gateway Service** : Point d'entrée unique API Gateway (Port `9999`).

### 2. Microservices Métier
* **User Service** (Port `8082`)
    * Base de données : MySQL (`db_user`).
* **Book Service** (Port `8081`)
    * Base de données : MySQL (`db_book`).
* **Emprunt Service** (Port `8085`)
    * Base de données : MySQL (`db_emprunter`).
    * **Rôle Kafka :** PRODUCTEUR. Publie un événement `EMPRUNT_CREATED` sur le topic `emprunt-created`.

### 3. Service de Notification (Nouveau)
* **Notification Service**
    * **Rôle Kafka :** CONSOMMATEUR. Écoute le topic `emprunt-created`.
    * Simule l'envoi d'un email en affichant les détails (Utilisateur, Livre, Date) dans la console.


