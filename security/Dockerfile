FROM adoptopenjdk/openjdk11:alpine-slim

COPY target/security-*.war /app.war
			  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]
