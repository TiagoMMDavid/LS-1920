@echo off

rename dbinfo.config dbinfo.bat
call dbinfo.bat
rename dbinfo.bat dbinfo.config

docker rm %container%