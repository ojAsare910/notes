FROM openjdk:21-slim

WORKDIR /app

COPY target/notes-1.jar notes-app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "notes-app.jar"]