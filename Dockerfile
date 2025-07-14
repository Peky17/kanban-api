# Using the official OpenJDK 17 image
FROM eclipse-temurin:17-jdk-alpine

#Manteiner info
LABEL maintainer="Enrique <ebgvdeveloper@gmail.com>"

# Create a directory for the app
WORKDIR /congratulations-api

# Copy the jar file into the container
COPY target/congratulations-api-0.0.1-SNAPSHOT.jar /congratulations-api/congrats-api.jar

# Expose the port the app runs in
EXPOSE 8080

#execute the application
ENTRYPOINT ["java","-jar","congrats-api.jar"]