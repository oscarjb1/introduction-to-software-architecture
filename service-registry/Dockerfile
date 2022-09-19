FROM adoptopenjdk/openjdk11:alpine-slim

COPY target/service-registry-*.jar /app.jar
			  
CMD ["java", "-jar", "/app.jar"]