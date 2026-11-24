# RestaurantService

Microservice Spring Boot 4 (Java 21) — gestion des restaurants et de leurs menus.

## Prérequis
- PostgreSQL avec la base `dbrestaurant` : `CREATE DATABASE dbrestaurant;`
- Renseigner le mot de passe dans `src/main/resources/application.yml`
- RegisteryService démarré sur http://localhost:8761

## Lancer
```
./mvnw spring-boot:run
```
Port : 8083 — enregistré dans Eureka sous `RESTAURANTSERVICE`.

## Endpoints
| Méthode | URL | Description |
|---|---|---|
| POST | `/api/restaurants` | Créer un restaurant |
| GET | `/api/restaurants[?ville=Tunis]` | Lister (filtre optionnel par ville) |
| GET | `/api/restaurants/{id}` | Détail d'un restaurant |
| PUT | `/api/restaurants/{id}` | Modifier un restaurant |
| DELETE | `/api/restaurants/{id}` | Supprimer un restaurant (et ses menus) |
| POST | `/api/restaurants/{id}/menus` | Ajouter un menu |
| GET | `/api/restaurants/{id}/menus` | Lister les menus du restaurant |
| GET | `/api/restaurants/{id}/menus/{menuId}` | Détail d'un menu |
| PUT | `/api/restaurants/{id}/menus/{menuId}` | Modifier un menu |
| DELETE | `/api/restaurants/{id}/menus/{menuId}` | Supprimer un menu |

Exemples :
```json
{ "nom": "Chez Salah",
  "adresse": { "numero": "12B", "rue": "Rue de la Liberté", "cite": "Cité Ennasr", "ville": "Tunis" } }
```
```json
{ "nom": "Couscous", "prix": 18.50 }
```
