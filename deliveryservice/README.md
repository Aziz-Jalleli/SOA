# DeliveryService

Delivery microservice of the **MyFood** application (Spring Boot 3, Java 17, Eureka client, PostgreSQL).

## Run

1. Create the PostgreSQL database `dbdelivery` (user `postgres`, password `123456`, see `application.yml`).
2. Start `service-registry` (http://localhost:8761).
3. Start `CourtierService` (optional, see `courtier.integration-enabled`).
4. Run `DeliveryServiceApplication` (port **8085**).

## API (`/deliveries`)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/deliveries` | Create a delivery (`PENDING`) |
| GET | `/deliveries?status=` | List deliveries, optional status filter |
| GET | `/deliveries/{id}` | Get by id |
| GET | `/deliveries/order/{orderId}` | Get by order |
| GET | `/deliveries/courier/{cin}` | Deliveries of a courier |
| PUT | `/deliveries/{id}/assign` | Assign a courier `{ "courierCin": "..." }`; body optional, an available courier is picked automatically |
| PUT | `/deliveries/{id}/pickup` | `ASSIGNED` -> `PICKED_UP` |
| PUT | `/deliveries/{id}/deliver` | `PICKED_UP` -> `DELIVERED` |
| PUT | `/deliveries/{id}/cancel` | Cancel (not allowed once delivered) |

Example body for `POST /deliveries`:

```json
{ "orderId": 1, "ticketId": 1, "restaurantId": 1, "consumerId": 1, "deliveryAddress": "12 Rue de Tunis" }
```

## Gateway route

```yaml
- id: DeliveryService
  uri: lb://DeliveryService
  predicates:
    - Path=/deliveries/**
```
