# ---------- Build Stage ----------
FROM maven:3.9.10-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN mkdir -p uploads

COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -Dserver.port=$PORT -jar app.jar"]