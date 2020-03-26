@echo off

rename dbinfo.config dbinfo.bat
call dbinfo.bat
rename dbinfo.bat dbinfo.config

cls
docker exec -it %container% psql -U postgres