#bin/bash
container_name="bank-mssql-db"
db_start_delay=15

docker-compose up --build -d ${container_name} || exit

sleep ${db_start_delay}

docker cp initial-scripts/init-database.sql ${container_name}:opt/init-database.sql || exit
docker exec ${container_name} //opt/mssql-tools/bin/sqlcmd -S 127.0.0.1 -U sa -P Mfcatl007 -d master -i //opt/init-database.sql || exit

./build-start.sh || exit