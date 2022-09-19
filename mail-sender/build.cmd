CALL ./mvnw package -DskipTests
CALL docker build -t mail-sender .