# Build stage
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# Copy Maven wrapper and pom to cache dependencies
COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN chmod +x mvnw

# Copy source and build application
COPY src ./src
RUN ./mvnw package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
