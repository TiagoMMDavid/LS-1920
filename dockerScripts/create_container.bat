@echo off
rename dbinfo.config dbinfo.bat
call dbinfo.bat
rename dbinfo.bat dbinfo.config

:: -p -> PORTA | -e -> VARIÁVEIS | -v -> PASTA ROOT | -d -> CONFIGURAÇÃO
docker run --name %container% -e POSTGRES_USER="%user%" -e POSTGRES_PASSWORD="%password%" -e POSTGRES_DB="%dbname%"  -p 5432:5432 -d postgres