FROM adoptopenjdk/openjdk11:alpine-slim

COPY target/3pary-app-*.jar /app.jar
			  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
