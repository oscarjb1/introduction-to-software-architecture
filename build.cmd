@echo off

CALL cd security
CALL build
CALL docker build -t security .

CALL cd ../ecommerce-app
CALL build
CALL docker build -t ecommerce-app .

CALL cd ../service-registry
CALL build
CALL docker build -t service-registry .

CALL cd ../api-gateway
CALL build
CALL docker build -t api-gateway .

CALL cd ../security
CALL build
CALL docker build -t security .

CALL cd ../crm-api
CALL build
CALL docker build -t crm-api .

CALL cd ../mail-sender
CALL build
CALL docker build -t mail-sender .

CALL cd ../webhook-notif
CALL build
CALL docker build -t webhook-notif .

CALL cd ../3pary-app
CALL build
CALL docker build -t 3pary-app .

CALL cd ../ftp-payment-pooling
CALL build
CALL docker build -t ftp-payment-pooling .
