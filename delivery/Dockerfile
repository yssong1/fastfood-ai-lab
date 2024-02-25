FROM eclipse-temurin:17-jdk-jammy

# Set the working directory.
WORKDIR /app

# Copy the jar to the production image from the builder stage.
COPY /target/*.jar app.jar

# Sets the Spring Boot profile to docker
ENV spring.profiles.active=docker

# Expose the port.
EXPOSE 8080

# Run the application.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
