FROM eclipse-temurin:25-jre

WORKDIR /app

# Glob, not a pinned filename: the artifact name embeds <version>, so a
# version bump in the pom would otherwise break the image build.
COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
