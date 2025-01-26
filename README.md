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

Pre-requisites
----------
- Docker and Docker-compose installed on your machine
- Git (with submodule support)

Make sure to clone the current repository recursively so you also get the BACK and FRONT submodules:
```
git clone --recurse-submodules <url>
```

If you already cloned without the --recurse-submodules, just run:
```
git submodule update --init --recursive
```

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


Step 4, 5, 6, 7: Traefik
----------
All the configurations for Traefik are in the [traefik/README.md](traefik/README.md) folder.


Optional step 1: Management UI
------------------------------
A COMPLETER
You have documented how to use your solution.

Optional step 2: Integration API - static Web site
--------------------------------------------------
For this step, we have integrated the dynamic server with the API. 

We have added javascript code to the static website to fetch data from the API and display it on the website.

The code can be found in the [biasfinder-frontend](biasfinder-frontend) folder.

A COMPLETER metre un exemple de code