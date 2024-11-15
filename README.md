# code-with-quarkus

A Quarkus-based hotel booking system.

## Tech Stack

- Java 21
- Quarkus 3.16.3
- Maven
- H2 Database

## API Endpoints

### Get all rooms

```bash
curl -X GET http://localhost:8080/api/rooms
```

### Book a room
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"roomId": 1, "guestName": "John Doe", "checkInDate": "2023-12-01", "checkOutDate": "2023-12-05"}'
```
### Get booking by ID
```bash
curl -X GET http://localhost:8080/api/bookings/{id}
```

### Cancel booking
``` bash 
curl -X DELETE http://localhost:8080/api/bookings/1
```

