# ---------- Build Stage ----------
FROM maven:3.9.10-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only the POM first — this layer is cached as long as pom.xml doesn't change.
# Maven will download all dependencies once and reuse the Docker cache on subsequent builds,
# eliminating repeated hits to Maven Central that cause 429 rate-limit errors on Render.
COPY pom.xml .
RUN mvn dependency:go-offline --no-transfer-progress -B

# Now copy the source code and build.
# If only source changes, the expensive dependency download layer above is still cached.
COPY src ./src
RUN mvn clean package -DskipTests --no-transfer-progress -B

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN mkdir -p uploads

COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]