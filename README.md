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

A more detailed description of the website and of the configuration of the nginx server can be found in the [static_server/README.md](static_server/README.md) file.


HTTP API server
----------
This report details the implementation of an HTTP API server built using Javalin. The API manages a collection of Photocards and provides CRUD (Create, Read, Update, Delete) operations. The API is integrated with a PostgreSQL database and is containerized using Docker for easy deployment.
You will find the detailed description of the API server in the [app-dai/README.md]([app-dai/README.md) file.

TO COMPLETE [app-dai/README.md]([app-dai/README.md) file.


Traefik 
----------
Steps 4 to 7 are more developed in the [traefik/README.md](traefik/README.md).


Optional step 1: Management UI
------------------------------


Optional step 2: Integration API - static Web site
--------------------------------------------------
