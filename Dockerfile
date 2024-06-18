# Dockerfile
FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/docker-java-api.jar /app/docker-java-api.jar

CMD ["java", "-jar", "docker-java-api.jar"]