HTTP API server
=============================

The HTTP API server is built using Javalin. The API manages a collection of Photocards and provides CRUD (Create, Read, Update, Delete) operations. 

The API is integrated with a PostgreSQL database and is containerized using Docker for easy deployment.

Database
----------
The database is a PostgreSQL database. The database schema is defined in the submodule repository from the BDR project. 
A COMPLETER
![Schema Conceptuel]()

Further information about the database schema can be found in the the report of the BDR project.

CRUD operations
----------

The API provides the following CRUD operations:

| Method | Path                                         | Description                                                                                   | Example                                                                                                        |
|--------|----------------------------------------------|-----------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| GET    | /api/allcards                                | Get all photocards                                                                            | [com/example/appdai/controller/PcController.java:54](com/example/appdai/controller/PcController.java:54)       |
| GET    | /api/groups/{groupId}/artists                | Get a list of artists for the specified group ID                                              | [com/example/appdai/controller/GroupController.java:65](com/example/appdai/controller/GroupController.java:65) |
| POST   | /api/officialsource/propose                  | Proposes a new official source by accepting relevant data and passing it to the service layer | [com/example/appdai/controller/OfController.java:53](com/example/appdai/controller/OfController.java:53)       |
| POST   | /api/users/{userId}/photocards               | Add or update a photocard in the wishlist/collection                                          | [com/example/appdai/controller/UserController.java:67](com/example/appdai/controller/UserController.java:67)   |
| PATCH  | /api/admin/accept                            | Accept proposed photocards, updates one parameter from true to false                          | [com/example/appdai/controller/UserController.java:145](com/example/appdai/controller/UserController.java:145) |
| DELETE | /api/users/{userId}/photocards/{photocardId} | Delete a photocard from the wishlist/collection                                               | [com/example/appdai/controller/UserController.java:88](com/example/appdai/controller/UserController.java:88)   |


API Testing
----------
During the entire project, we used Bruno to test and work on the API.
The tests can be found in the [collection_biasbinder_api](collection_biasbinder_api) folder containing the Bruno collections.

API Dockerfile
----------
The API server is containerized using Docker. The [Dockerfile](Dockerfile) was created to build the image of the API server in accordance with the project requirements for BDR class. 
The deployment of the API server is done using the docker-compose file in the root of the project.
