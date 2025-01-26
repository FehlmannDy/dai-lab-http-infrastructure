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

![docker UI](images/app-docker-ui.png)

### Build the Docker Image


Navigate to the app-docker-ui/ and build the Docker image using Docker Compose.

```
docker-compose build
```

### Start the Application

After the build is complete, start the application using the following command:

```
docker-compose up
```

### Access the Application

Once the container is up and running, access the application by visiting:

http://localhost:3000

Now you can manage your containers


Optional step 2: Integration API - static Web site
--------------------------------------------------

Home :

    GET /api/groupslist: Retrieves the list of groups.
    GET /api/groups/${groupId}/artists: Retrieves artists for the selected group.
    GET /api/photocards?page=${page}&size=24: Retrieves photocards with pagination.
    GET /api/users/${userId}/collection: Retrieves the user's owned and wishlist photocards.
    POST /api/users/${userId}/photocards
    DELETE /api/users/${userId}/photocards: Adds/removes photocards from the user's collection.
    POST /api/user/photocard: Adds/removes photocard to/from the user's collection.

Proposed Photocard :

    GET /api/groupslist: Retrieves the list of groups.
    GET /api/groups/${groupId}/artists: Retrieves artists for a specific group.
    GET /api/groups/official-sources: Retrieves official sources for a group.
    POST /api/photocards/proposecard: Proposes a photocard.
    POST /api/officialsource/propose: Proposes an official source.

User Profile :

    GET /api/users/{userId}/collection: Retrieves the list of owned photocards.
    GET /api/users/{userId}/wishlist: Retrieves the list of wishlist photocards.

Admin Page:

    GET /api/proposedphotocards: Retrieves the list of proposed photocards.
    PATCH /api/admin/accept: Accepts the selected photocards.
    DELETE /api/admin/reject: Rejects the selected photocards.

Optional step 2: Integration API - static Web site
--------------------------------------------------
For this step, we have integrated the dynamic server with the API. 

We have added javascript code to the static website to fetch data from the API and display it on the website.

The code can be found in the [biasfinder-frontend](biasfinder-frontend) folder.

Home :

    GET /api/groupslist: Retrieves the list of groups.
    GET /api/groups/${groupId}/artists: Retrieves artists for the selected group.
    GET /api/photocards?page=${page}&size=24: Retrieves photocards with pagination.
    GET /api/users/${userId}/collection: Retrieves the user's owned and wishlist photocards.
    POST /api/users/${userId}/photocards
    DELETE /api/users/${userId}/photocards: Adds/removes photocards from the user's collection.
    POST /api/user/photocard: Adds/removes photocard to/from the user's collection.

Proposed Photocard :

    GET /api/groupslist: Retrieves the list of groups.
    GET /api/groups/${groupId}/artists: Retrieves artists for a specific group.
    GET /api/groups/official-sources: Retrieves official sources for a group.
    POST /api/photocards/proposecard: Proposes a photocard.
    POST /api/officialsource/propose: Proposes an official source.

User Profile :

    GET /api/users/{userId}/collection: Retrieves the list of owned photocards.
    GET /api/users/{userId}/wishlist: Retrieves the list of wishlist photocards.

Admin Page:

    GET /api/proposedphotocards: Retrieves the list of proposed photocards.
    PATCH /api/admin/accept: Accepts the selected photocards.
    DELETE /api/admin/reject: Rejects the selected photocards.