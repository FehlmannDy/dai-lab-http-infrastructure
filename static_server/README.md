Static Website
=============================

The website is a very simple html front to announce the arrival of our app: BiasBinder BST.

nginx.conf
----------
The content of the nginx.conf file is mainly focussed on the configuration of the server.

The server is listening on port 80 and the root directory is the 'static_server' folder. 

It is also configured to serve the index.html file when the root directory is requested, and to serve 404 error when a file or directory is not found.

```
server {
    listen 80;
    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ =404;
    }
}
```


dockerfile
----------
The dockerfile is used to build the docker image containing the nginx server.

The image is based on the official nginx image, the nginx.conf file and the website files are copied into the container.

```
COPY ./static_template/ /usr/share/nginx/html/
COPY nginx_config/nginx.conf /etc/nginx/nginx.conf
```

The image can be built and run with the following commands:

```
docker build -t static-html-nginx .
docker run -d -p 8080:80 static-html-nginx
```

The website can then be accessed at http://localhost:8080


Website
----------
View of the website:
![Website Front](resources_report/static_website_front.png)