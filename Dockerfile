# Use an official OpenJDK image
FROM openjdk:21-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy the pom.xml and src directory to the container
COPY pom.xml .
COPY src ./src

# Run Maven to build the application
RUN mvn clean package

# List files in the target directory (for debugging purposes)
RUN ls -l target

# Use an official OpenJDK image to run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY --from=build /app/target/expense-tracker-0.0.1-SNAPSHOT.jar /app/expense-tracker.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/expense-tracker.jar"]
