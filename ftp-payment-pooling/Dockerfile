FROM adoptopenjdk/openjdk11:alpine-slim

COPY target/ftp-payment-pooling-*.jar /app.jar
			  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
