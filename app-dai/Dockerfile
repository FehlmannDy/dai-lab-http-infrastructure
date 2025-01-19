# 1) Use Maven + Java 17 to build
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Create app directory inside the container
WORKDIR /app

# Copy the pom.xml first and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Now copy source code
COPY src ./src

# Build the app (skip tests to speed up container builds)
RUN mvn clean package -DskipTests

# 2) Use a lightweight JRE image for runtime
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="BiasBinder - BST team"

# Create a directory for the jar
WORKDIR /app

# Copy the built jar from the 'build' stage
# Adjust the jar name if your final artifact is different
COPY --from=build /app/target/app-dai-0.0.1-SNAPSHOT.jar ./service.jar

# Expose the Javalin port your code starts on (7070)
EXPOSE 7070

# Optionally set environment variables for DB, if needed
# ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/biasbinder_db
# ENV SPRING_DATASOURCE_USERNAME=biasbinder_dev
# ENV SPRING_DATASOURCE_PASSWORD=biasbinder

# By default, run the jar
ENTRYPOINT ["java", "-jar", "service.jar"]
