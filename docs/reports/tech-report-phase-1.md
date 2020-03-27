# Relatório técnico da Fase 1

## Introdução

Este documento contém os aspectos relevantes do desenho e implementação da fase 1 do projecto de LS.

## Modelação da base de dados

### Modelação conceptual ###

O seguinte diagrama apresenta a modelo entidade-associação para a informação gerida pelo sistema. 

![alt text](https://i.imgur.com/3rJfrFg.png "EA Diagram")

Destacam-se os seguintes aspectos deste modelo:

* Uma entidade USERS, com atributos uid (chave primária), email, e name.
* Uma entidade fraca BOOKING, com atributos bid (chave primária), begin_inst e end_inst. Esta entidade encontra-se dependente de ROOM e USERS, visto que cada booking tem de ter a si associado um room (não se pode reservar nada), que, por sua vez, tem de ter um utilizador que o regista.
* Uma entidade ROOM, com atributos rid (chave primária), location, name, description e capacity.
* Uma associação ROOMLABEL que contém as chaves primárias de ROOM, e de LABEL.
* Uma entidade LABEL, composta pelos atributos lid (chave primária) e name.

O modelo conceptual apresenta ainda as seguintes restrições:

* Uma relação N - 1 entre BOOKING e USERS, porque cada booking tem de ter a si associado um user.
* Uma relação N - 1 entre BOOKING e ROOM, porque cada booking tem de ter a si associado um user.
* Uma relação N - N entre ROOM e LABEL, porque cada room pode ter associado a si um número indeterminado de labels, e as labels, por sua vez, podem também ser aplicadas a vários rooms.
    
### Modelação física ###

O modelo físico da base de dados está presente neste [link](../sqlScripts/createTable.sql).

Destacam-se os seguintes aspectos deste modelo:

* Uma tabela USERS, com chave primária uid (que se auto-incrementa), um atributo name, que não pode ser nulo, e uma chave candidata email.
* Uma tabela ROOM, com chave primária rid (que se auto-incrementa), com atributos name (que não pode ser nulo), description, location e capacity (que não pode ser nulo).
* Uma tabela LABEL, com chave primária lid (que se auto-incrementa) e um atributo designado por name, que é uma chave candidata, e não pode ser nulo.
* Uma tabela ROOMLABEL, que relaciona a tabela ROOM, e a tabela LABEL, contém como chaves estrangeiras lid e rid, que fazem também parte da chave primária.
* Uma tabela BOOKING, com chave primária bid (que se auto-incrementa), chaves estrangeiras uid (de USERS), e rid de (ROOM), e atributos begin_inst, e end_inst. Para estes últimos dois são efetuadas verificações para nos certificarmos que não se introduz um valor de ínicio maior que o valor de finalização (e vice-versa), que a duração (em minutos) de ambos, é divisível por 10, e que a diferença, também em minutos, entre os dois, é maior ou igual a 10. 

## Organização do software

### Processamento de comandos

(_describe the command handling interface_)

(_describe any additional classes used internally by the command handlers_)

(_describe how command parameters are obtained and validated_)

### Encaminhamento dos comandos

(_describe how the router works and how path parameters are extracted_)

### Gestão de ligações

(_describe how connections are created, used and disposed_, namely its relation with transaction scopes).

### Acesso a dados

(_describe any created classes to help on data access_).

(_identify any non-trivial used SQL statements_).

### Processamento de erros

(_describe how errors are handled and communicated to the application user_).

## Avaliação crítica

(_enumerate the functionality that is not concluded and the identified defects_)

(_identify improvements to be made on the next phase_)
