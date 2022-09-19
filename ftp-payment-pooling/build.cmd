CALL ./mvnw package -DskipTests
CALL docker build -t ftp-payment-pooling .