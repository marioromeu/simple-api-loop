# Dockerfile
FROM alpine/java:21-jdk

WORKDIR /app

COPY target/simple-api-loop-0.0.2-SNAPSHOT.jar /app/docker-java-api.jar

CMD ["java", "-jar", "docker-java-api.jar"]
