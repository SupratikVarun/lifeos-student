# Build stage with JDK 21
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Copy source code and build production jar
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# Runtime stage with lightweight JRE 21
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Ensure uploads directory exists
RUN mkdir -p uploads

# Copy compiled jar
COPY --from=builder /app/target/lifeos-student-backend-*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
