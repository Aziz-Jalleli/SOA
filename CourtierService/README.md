# CourtierService

Microservice Spring Boot 4 (Java 21) — gestion des courtiers et de leur position GPS.

## Prérequis
- PostgreSQL avec la base `dbcourtier` : `CREATE DATABASE dbcourtier;`
- Renseigner le mot de passe dans `src/main/resources/application.yml`
- RegisteryService démarré sur http://localhost:8761

## Lancer
```
./mvnw spring-boot:run
```
Port : 8082 — enregistré dans Eureka sous `COURTIERSERVICE`.

## Endpoints (`/api/courtiers`)
| Méthode | URL | Description |
|---|---|---|
| POST | `/api/courtiers` | Créer un courtier |
| GET | `/api/courtiers` | Lister tous les courtiers |
| GET | `/api/courtiers/disponibles` | Lister les courtiers disponibles |
| GET | `/api/courtiers/{cin}` | Détail d'un courtier |
| PUT | `/api/courtiers/{cin}` | Modifier un courtier |
| PATCH | `/api/courtiers/{cin}/disponibilite?disponible=true` | Changer la disponibilité |
| PUT | `/api/courtiers/{cin}/location` | Mettre à jour la position |
| DELETE | `/api/courtiers/{cin}` | Supprimer un courtier |

Exemple :
```json
{ "cin": "12345678", "nom": "Ali Ben Salah", "telephone": "+21620123456",
  "disponible": true, "location": { "longitude": 10.1815, "latitude": 36.8065 } }
```
