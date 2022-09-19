CALL ./mvnw package -DskipTests
CALL docker build -t service-registry .