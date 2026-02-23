FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY . .

# actually should not be built on the server, but this is not currently the scope
RUN ./mvnw clean package

FROM eclipse-temurin:21-jre AS runtime

# Copy the pre-built JAR from the target directory
COPY --from=build target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
