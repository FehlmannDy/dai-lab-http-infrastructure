DAI Lab - HTTP infrastructure - Documentations
=============================

Students
----------
- Fehlmann Dylan
- Halimi Elbunita
- Stelcher Stan

Introduction
----------
TO COMPLETE : Add a summary of the project with the main goals and the technologies used from each step.

Static Website
----------
The docker image containing the static HTTP server nginx is in the [static_server](static_server) folder.

A more detailed description of the website and of the configuration of the nginx server can be found in the [static_server/README.md](static_server/README.md) file.

Docker compose
----------
The [docker-compose](docker-compose.yml) file can be found in the root of the project. It contains the configurations to deploy the different services of the project.

The services are:
- reverse-proxy
- static_server
- db (PostgreSQL)
- api (Springboot/Javalin)
- front-end (React)

To build the project:
```
docker-compose up -d --build 
```

Commands to run the project are:
```
docker-compose up

```

To stop the project:
```
docker-compose down
```

Static Website
----------
The docker image containing the static HTTP server nginx is in the [static_server](static_server) folder.


Step 4: Reverse proxy with Traefik
----------


Step 5: Scalability and load balancing
----------


Step 6: Load balancing with round-robin and sticky sessions
----------


Step 7: Securing Traefik with HTTPS
----------



Optional step 1: Management UI
------------------------------


Optional step 2: Integration API - static Web site
--------------------------------------------------
