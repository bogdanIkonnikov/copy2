FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /build/libs/copy2-0.0.1-SNAPSHOT.jar /app/copy2.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "copy2.jar"]
