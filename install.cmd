@echo off

CALL echo Depurando configuracion existente
CALL docker-compose down
CALL docker system prune -a -f
CALL docker volume prune -f
CALL docker-compose up mysql -d

CALL echo "Incializando MySQL, favor espere..."
CALL timeout 30 /nobreak
CALL docker cp ./sql/ecommerce.sql mysql:/home/ecommerce.sql
CALL docker exec -i mysql /bin/bash -c "mysql -uroot -p1234 < /home/ecommerce.sql"

CALL docker-compose down