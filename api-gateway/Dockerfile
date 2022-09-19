FROM adoptopenjdk/openjdk11:alpine-slim

COPY target/api-gateway-*.jar /app.jar
			  
CMD ["java", "-jar", "/app.jar"]