@echo off
set container_name=bank-mssql-db
set /A db_start_delay=15

docker-compose up -d %container_name%

timeout /t %db_start_delay%

docker cp initial-scripts/init-database.sql %container_name%:opt/init-database.sql
docker exec %container_name% //opt/mssql-tools/bin/sqlcmd -S 127.0.0.1 -U sa -P Mfcatl007 -d master -i //opt/init-database.sql

call build-start.bat