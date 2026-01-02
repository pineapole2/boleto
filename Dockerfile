# etapa 1 - build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw -B -q package -DskipTests

# etapa 2 - runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
