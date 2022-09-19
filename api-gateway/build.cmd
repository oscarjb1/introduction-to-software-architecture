CALL ./mvnw package -DskipTests
CALL docker build -t api-gateway .