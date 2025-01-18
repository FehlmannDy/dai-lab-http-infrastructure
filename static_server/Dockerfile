FROM nginx:latest

# Set the working directory to /usr/share/nginx/html, which is the default NGINX document root
WORKDIR /usr/share/nginx/html

# Copy the static files to the container
COPY ./static_template/ /usr/share/nginx/html/

# Copy the custom nginx configuration file to the container
COPY nginx_config/nginx.conf /etc/nginx/nginx.conf

# Expose port 80 to be able to access the web server
EXPOSE 80

# Start the nginx server
CMD ["nginx", "-g", "daemon off;"]