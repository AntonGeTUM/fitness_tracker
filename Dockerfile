FROM --platform=linux/arm64 eclipse-temurin:21 AS build
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests

FROM --platform=linux/arm64 eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
RUN useradd -r appuser
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]