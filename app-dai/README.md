HTTP API server
=============================

Implementation Overview
----------
### Technology Stack
- Javalin : Used to create the HTTP API server.
- Spring Boot : Used to manage dependencies and configure the application.
- PostgreSQL : Used for the database.
- Docker & Docker Compose – Used for containerizing the API.
- Bruno : Used for testing the API.

### Project Structure
```
com.example.appdai
│── controller/   # Handles HTTP requests (PcController.java)
│── model/        # Data models (Photocard.java, User.java, Artist.java, Group.java)
│── repository/   # Database interaction layer (PcRepository.java)
│── service/      # Business logic (PcService.java)
│── config/       # Javalin configuration (JavalinConfig.java)
│── main app      # AppDaiApplication.java
```

AAPI Endpoints & Functionality
----------
The API provides the following endpoints:
### Read (GET) Operations
| Endpoint | Description |
| --- | --- |
| ```GET /api/coucoutoi``` | Returns the first photocard record |
| ```GET /api/hello``` | Returns a test greeting message. |
| ```GET /api/allcards``` | Returns a list of all photocard names. |
| ```GET /api/allcardswithtype``` | Returns all photocards with their details. |

### Create (POST) Operations
(To be implemented if needed, e.g., POST /api/addcard)

### Update (PUT) Operations
(To be implemented if needed, e.g., PUT /api/updatecard/{id})

### Delete (DELETE) Operations
(To be implemented if needed, e.g., DELETE /api/deletecard/{id})

Code Walkthrough
----------
### Main Application (```AppDaiApplication.java```)
This class is the entry point of the application: It initializes the Javalin web server and registers routes for handling HTTP requests.

Key points:
- Starts the server on port 7070.
- Uses Spring Boot to manage dependencies and start the app.
- Calls ```registerRoutes(app)``` to configure the API endpoints.

### Controller Layer (```PcController.java```)
This class defines  HTTP endpoints (routes) for managing Photocards and uses PcService to handle business logic.
The responses to the HTTP requests are in JSON format.

Key points:
- Uses GET edpoints to retrieve data from the database.
- Uses POST, PUT, and DELETE endpoints to modify the database.
- Uses status codes (200 OK) for responses.

### Service Layer (```PcService.java```)
This class contains the business logic for managing Photocards. It interacts with the database through PcRepository.

Key points:
- Calls repository layer (```PcRepository```) to retrieve data from PostgreSQL.
- Prepares data before returning it to the controller.
- Handles error prevention by ensuring repository methods return valid values.

### Repository Layer (```PcRepository.java```)
This class interacts with the PostgreSQL database to perform CRUD operations on the Photocard table.
It executes SQL queries to retrieve, insert, update, and delete records.
It uses the JdbcTemplate class to interact with the database, but also for security reasons (SQL injection prevention).

Key points:
- Uses JdbcTemplate to execute SQL queries.
- Retrieves photocard records using SELECT statements.
- Handles exceptions to prevent crashes.

### Model Layer (```Photocard.java```)
Defines data structures for Photocards, Users, Artists, Groups. It represents database entities as Java objects.

Key points:
- Uses Encapsulation (```private``` fields with ```getters``` & ```setters```).
- Ensures data consistency in API responses.


Dockerization
----------
TO COMPLETE: need to add the dockerization part here.

Testing the API
----------
TO COMPLETE: Need to add Bruno testing here.

Conclusion
----------
TO COMPLETE : Add a summary of the project with the main goals and the technologies used from each step.