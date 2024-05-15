FROM openjdk:20-jdk-slim

WORKDIR /app

COPY target/PJI-1.0-SNAPSHOT.jar /app/

COPY src/main/resources/apks /app/resources/apks

CMD ["java", "-jar", "PJI-1.0-SNAPSHOT.jar", "resources/apks"]
