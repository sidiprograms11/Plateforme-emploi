# Plateforme Emploi – Coffre numérique sécurisé

## Description

Plateforme sécurisée de gestion de CV et de documents professionnels développée pour un projet de stage en cybersécurité.

L'objectif est de permettre aux utilisateurs de stocker, partager et signer des documents de manière sécurisée grâce à des mécanismes cryptographiques modernes.

Chaque document est chiffré avant stockage et ne peut être lu que par les utilisateurs autorisés.

---

# Fonctionnalités principales

* Inscription sécurisée des utilisateurs
* Génération automatique de clés cryptographiques
* Coffre numérique personnel
* Upload sécurisé de documents
* Partage sécurisé entre utilisateurs
* Signature numérique de documents
* Vérification de signature
* Historique d'accès aux documents
* Gestion des utilisateurs autorisés

---

# Architecture du projet

Le projet est composé de deux applications :

```
Plateforme-emploi
│
├── demo-backend
│   └── Spring Boot API sécurisée
│
└── frontend
    └── Application Angular
```

---

# Technologies utilisées

## Backend

* Spring Boot
* Spring Security
* JWT Authentication
* JPA / Hibernate
* PostgreSQL
* Maven

## Frontend

* Angular
* TypeScript
* Tailwind CSS

## Sécurité et cryptographie

* RSA 2048 (chiffrement asymétrique)
* AES 256 (chiffrement des documents)
* SHA-256 (hash des documents)
* Signature numérique
* Stockage sécurisé des clés privées

---

# Fonctionnement cryptographique

Lors de l'inscription d'un utilisateur :

1. Une paire de clés RSA est générée
2. La clé privée est chiffrée avec le mot de passe utilisateur
3. La clé publique est stockée en base

---

## Upload d'un document

1. Génération d'une clé AES
2. Chiffrement du document avec AES
3. Chiffrement de la clé AES avec la clé publique RSA de l'utilisateur
4. Stockage en base de données

---

## Lecture d'un document

1. Vérification du mot de passe
2. Déchiffrement de la clé privée utilisateur
3. Déchiffrement de la clé AES
4. Déchiffrement du document

---

## Signature d'un document

1. Calcul du hash SHA-256 du document
2. Signature du hash avec la clé privée
3. Stockage de la signature

---

## Vérification de signature

1. Recalcul du hash
2. Vérification de la signature avec la clé publique

---

# Base de données

Tables principales :

```
registered_users
stored_documents
document_key_access
document_signature
document_access_log
document_history
```

---

# API sécurisée

Les endpoints principaux :

```
POST /api/auth/register
POST /api/auth/login

POST /api/documents
POST /api/documents/{id}/read
POST /api/documents/{id}/sign
GET  /api/documents/{id}/verify

POST /api/documents/{id}/share
DELETE /api/documents/{id}/share/{userId}

GET /api/documents/me
GET /api/documents/shared-with-me
GET /api/documents/shared-by-me
```

Documentation disponible via Swagger.

---

# Installation du projet

## 1 – Backend

```
cd demo-backend
./mvnw spring-boot:run
```

Backend disponible sur :

```
http://localhost:8080
```

Swagger :

```
http://localhost:8080/swagger-ui.html
```

---

## 2 – Frontend

```
cd frontend
npm install
ng serve
```

Frontend disponible sur :

```
http://localhost:4200
```

---

# Base de données

PostgreSQL est utilisé.

Configurer dans :

```
application.properties
```

Exemple :

```
spring.datasource.url=jdbc:postgresql://localhost:5432/plateforme
spring.datasource.username=postgres
spring.datasource.password=postgres
```

---

# Démonstration sécurité

La plateforme démontre :

* chiffrement de bout en bout des documents
* gestion sécurisée des clés
* signature numérique
* contrôle d'accès cryptographique
* historique des accès

