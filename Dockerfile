# Dockerfile
FROM ghcr.io/graalvm/native-image-community:24 AS builder

WORKDIR /app

COPY target/simple-api-loop-0.0.1-SNAPSHOT.jar /app/docker-java-api.jar

CMD ["java", "-jar", "docker-java-api.jar"]
#RUN native-image --no-fallback -jar your-app.jar