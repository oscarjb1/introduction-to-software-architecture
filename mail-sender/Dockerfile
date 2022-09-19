FROM adoptopenjdk/openjdk11:alpine-slim

COPY target/mail-sender-*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]