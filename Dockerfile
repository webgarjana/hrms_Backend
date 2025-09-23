# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file
COPY target/empsys-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render uses PORT env variable)
ENV PORT 8080
EXPOSE $PORT

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]