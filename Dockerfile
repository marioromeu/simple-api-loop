# Dockerfile
FROM alpine/java:21-jdk

WORKDIR /app

COPY target/docker-java-api.jar /app/docker-java-api.jar

CMD ["java", "-jar", "docker-java-api.jar"]