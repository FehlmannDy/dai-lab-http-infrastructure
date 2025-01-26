Reverse Proxy - Traefik
=============================

The reverse proxy is used to manage the incoming requests and distribute them to the appropriate services. In this project, we use Traefik as a reverse proxy.
Later, we will also use Traefik for load balancing and securing the services.

Reverse Proxy
-------------
For this project, we use Traefik as a reverse proxy. Traefik is a modern HTTP reverse proxy and load balancer that makes deploying microservices easy. 
In our case, it manages the static server, the dynamic server, and the database server.

The configuration of Traefik is done in the `docker-compose`. 
```
  reverse-proxy:
    image: traefik:v2.11
    container_name: traefik
    ports:
      - "80:80"       # Redirection HTTP → HTTPS
      - "443:443"     # HTTPS
      - "8080:8080"   # Dashboard

```

Load Balancing
------------
For the load balancing, Traefik uses round-robin by default.

For the dynamic server, we just had to had the following labels to the `docker-compose` file:
```
    labels:
      - "traefik.http.services.api.loadBalancer.sticky.cookie=true"
      - "traefik.http.services.api.loadBalancer.sticky.cookie.name=api-session"
```

Securing Traefik
-----------
Finally, we secure Traefik with a basic authentication.

The following labels were needed in the application:

```
    labels:
      - "traefik.http.routers.api.rule=PathPrefix(`/api`)"
      - "traefik.http.routers.api.entrypoints=websecure"
      - "traefik.http.routers.api.tls=true"
      - "traefik.http.services.api.loadbalancer.server.port=7070"
```

In the revers-proxy : 

```
        volumes:
      - "/var/run/docker.sock:/var/run/docker.sock:ro"
      - "./traefik/traefik.yml:/etc/traefik/traefik.yml"
      - "./traefik/certs:/etc/traefik/certs"
```


We update the `traefik.yml` file with the following configuration:

```
tls:
  stores:
    default:
      defaultCertificate:
        certFile: /etc/traefik/certs/cert.pem
        keyFile: /etc/traefik/certs/privkey.pem
```
