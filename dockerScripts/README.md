Scripts de automatização de PostgreSQL para Docker for Windows.
====================================================================================

INTRODUÇÃO:
Os scripts incluídos com este ficheiro permitirão uma configuração mais fácil
de um servidor PostgreSQL utilizando o Docker for Windows. O servidor criado
encontrar-se-á em "localhost", na porta "5432". Com estes dados, juntamente
com os dados exclusivos à vossa base de dados (configuraveis em "dbinfo.config"),
poderão efetuar uma ligação à mesma através de JDBC, ou a partir de uma outra
aplicação de gestão de base de dados (ex. DBeaver).

====================================================================================

• COMO CONFIGURAR O SERVIDOR
   1 - Certificar que a versão do Windows instalado é:
	- Pro
	- Enterprise
	- Education (disponível no Microsoft Azuze de graça para alunos do ISEL)
   2 - Transferir e executar o Docker for Windows
   3 - Editar o ficheiro "dbinfo.config" com as configurações do vosso server
   4 - Abrir a linha de comandos (cmd.exe) na diretoria dos scripts, e excrever "create"
   5 - Aguardar a instalação dos ficheiros necessários para o servidor
   6 - Quando possível, executar o comando "docker ps -a" para verificar que o servidor encontra-se a correr
   7 - Feito! O servidor encontra-se (hopefully) a correr.

• COMO PARAR O SERVIDOR
   Abrir o CMD na diretoria dos scripts, e escrever "stop_container"

• COMO INICIAR O SERVIDOR
   Abrir o CMD na diretoria dos scripts, e escrever "run_container"

• COMO APAGAR O SERVIDOR
   Abrir o CMD na diretoria dos scripts, e escrever "remove_container"

• COMO ABRIR A JANELA DE COMANDOS PSQL
   Abrir o CMD na diretoria dos scripts, e escrever "psql"