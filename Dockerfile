# Runtime Dockerfile: copy pre-built jar from target/
FROM eclipse-temurin:21-jre
WORKDIR /app

# The application jar must be built locally before building this image.
# Run: .\mvnw.cmd -DskipTests package
COPY target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]




