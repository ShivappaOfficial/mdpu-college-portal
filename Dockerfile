# Use Java 17 (recommended for Spring Boot)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cache layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port (Render provides PORT env)
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/*.jar"]
