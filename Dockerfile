# Use the official Ubuntu image as a base
FROM ubuntu:latest

# Install OpenJDK 21 and Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build file and the source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/StructurizeBE-0.5.jar"]