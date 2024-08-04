# Use an official Maven image to build the application
FROM maven:3.8.6-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and src directory to the container
COPY pom.xml .
COPY src ./src

# Run Maven to build the application
RUN mvn clean package

# Use an official OpenJDK image to run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/expense-tracker-0.0.1-SNAPSHOT.jar /app/expense-tracker.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/expense-tracker.jar"]

