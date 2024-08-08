# Use the official OpenJDK 21 image as a base
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application jar file into the container
COPY target/StructurizeBE-0.5.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
