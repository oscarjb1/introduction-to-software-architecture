CALL ./mvnw package -DskipTests
CALL docker build -t 3pary-app .