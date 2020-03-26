@echo off
rename dbinfo.config dbinfo.bat
call dbinfo.bat
rename dbinfo.bat dbinfo.config

echo create database %testdbname%;\q>createtest.txt

docker exec -i db psql -U postgres<createtest.txt