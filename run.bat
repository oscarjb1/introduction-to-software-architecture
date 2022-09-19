

call docker-compose up service-registry -d
call docker-compose up mysql -d

CALL echo "Incializando servicios de infraestructura, espere por favor......"
CALL timeout 30 /nobreak

call docker-compose up -d
