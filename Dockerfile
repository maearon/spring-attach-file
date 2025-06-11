FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Install Maven and curl
RUN apt-get update && apt-get install -y maven curl && rm -rf /var/lib/apt/lists/*

# Copy Maven files
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

# Download dependencies
# RUN ./mvnw dependency:go-offline
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
# RUN ./mvnw clean package -DskipTests
RUN mvn clean package -DskipTests

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Development vs Production
CMD if [ "$SPRING_PROFILES_ACTIVE" = "development" ]; then \
      mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"; \
    else \
      java -jar target/*.jar; \
    fi