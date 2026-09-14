FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/authservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
