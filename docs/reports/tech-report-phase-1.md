# Relatório técnico da Fase 1

## Introdução

Este documento contém os aspectos relevantes do desenho e implementação da fase 1 do projecto de LS.

## Modelação da base de dados

### Modelação conceptual ###

O seguinte diagrama apresenta a modelo entidade-associação para a informação gerida pelo sistema. 

![alt text](https://github.com/isel-leic-ls/1920-2-LI42D-G08/blob/master/docs/images/EA_Diagram.png "EA Diagram")

Destacam-se os seguintes aspectos deste modelo:

* Uma entidade USERS, com atributos uid (chave), email, e name.
* Uma entidade fraca BOOKING, com atributos bid (chave), begin_inst (correspondente à data de início do booking) e end_inst (data de fim do booking). Esta entidade é fraca sendo dependente de ROOM, visto que cada booking está necessáriamente associado a uma sala. Por sua vez, a sua relação com USERS também é obrigatória pois só existe um booking se houver um utilizador responsável pela respetiva reserva.
* Uma entidade ROOM, com atributos rid (chave), location, name, description e capacity.
* Uma entidade LABEL, composta pelos atributos lid (chave) e name.
* Uma relação 1 - N entre USERS e BOOKING, porque cada pessoa pode ter vários bookings mas uma reserva só está associada a um utilizador.
* Uma relação 1 - N entre ROOM e BOOKING, dado que cada sala pode ter vários bookings.
* Uma relação N - N entre ROOM e LABEL, porque cada room pode ter associado a si um número indeterminado de labels, e as labels, por sua vez, podem também ser aplicadas a vários rooms.

O modelo conceptual apresenta ainda as seguintes restrições:

* As chaves primárias de todas as entidades, ou seja, os atributos ROOM(rid), LABEL(lid), BOOKING(bid) e USERS(uid) são caraterizados por um valor inteiro maior ou igual a zero que é auto-incrementado a cada inserção.
* A associação entre BOOKING e USERS é total pelo facto de ser necessário associar um utilizador a dado booking.
* A associação entre BOOKING e ROOM é total porque não é possível criar um booking sem ter um room associado ao mesmo.
* Os atributos USERS(email), ROOM(name) e LABEL(name) são chaves secundárias da respetiva entidade.
* Os atributos USERS(name), ROOM(location) e BOOKING(begin_inst, end_inst) são do tipo NOT NULL.
* A associação ROOMLABEL tem chaves estrangeiras que fazem referência a ROOM(rid) e LABEL(lid).
* A entidade BOOKING tem chaves estrangeiras que referenciam ROOM(rid) e USERS(uid)
    
### Modelação física ###

O modelo físico da base de dados está presente neste [link](https://github.com/isel-leic-ls/1920-2-LI42D-G08/blob/master/sqlScripts/createTable.sql).

Destacam-se os seguintes aspectos deste modelo:

* Uma tabela USERS, com chave primária uid (que se auto-incrementa), um atributo name, que não pode ser nulo, e uma chave candidata email.
* Uma tabela ROOM, com chave primária rid (que se auto-incrementa), com atributos name (atributo não nulo), description, location (não nulo) e capacity.
* Uma tabela LABEL, com chave primária lid (que se auto-incrementa) e um atributo designado por name, que é uma chave candidata, e não pode ser nulo.
* Uma tabela ROOMLABEL, que relaciona a tabela ROOM com a tabela LABEL, contém como chaves estrangeiras lid e rid, que fazem também parte da chave primária.
* Uma tabela BOOKING, com chave primária bid (que se auto-incrementa), chaves estrangeiras uid (de USERS), e rid de (ROOM), e atributos begin_inst, e end_inst que representam respetivamente a data de início e de fim de um booking. Para estes últimos dois são efetuadas verificações para nos certificarmos que não se introduz um valor de ínicio maior que o valor de finalização (e vice-versa), que a duração (em minutos) de ambos, é divisível por 10, e que a diferença, também em minutos, entre os dois, é maior ou igual a 10. 

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

De modo a averiguar o correto funcionamento do programa é necessário efetuar o processamento de erros e comunicá-los ao utilizador do programa.

Sendo assim, há verificações feitas pelo código que certificam-se de afetar o programa. Estes erros podem ser leves, mostrando apenas uma mensagem de erro, ou em casos mais graves, lançando uma exceção que consequentemente para a aplicação.

Relativamente a erros ligeiros, na leitura dos comandos da aplicação é feita a simples verificação do número de argumentos passados no comando. Visto que cada comando é definido pela sequência {method} {path} {parameters}, em que {parameters} nem sempre é obrigatório conclui-se que todos os comandos têm de ter entre dois a três argumentos. Logo, caso esta restrição não seja cumprida é apresentada na consola uma mensagem de erro ao utilizador.

Passando às possíveis exceções, as mesmas estão dispersas entre as várias classes do projeto. Quando o programa passa a primeira verificação referida anteriormente, vai instanciar vários objetos do tipo CommandRequest, Path, Parameters, CommandHandler e CommandResult em função dos argumentos dados. Visto que os argumentos recebidos podem vir com um formato errado, todas as classes referidas efetuam verificações que se certificam que o dado objeto foi inicializado corretamente. Caso isto não acontença será lançada uma exceção adequada ao tipo de erro.

Por fim, quanto ao acesso à base de dados, dentro dos CommandHandlers, caso haja qualquer exceção SQL apanhada no decorrer do código, é feito um rollback dos dados que podem ter sido inseridos, e altera-se o CommandResult em função do erro sucedido. Ou seja, altera-se o booleano _success_ de CommandResult e à string _title_ adiciona-se a mensagem de erro. Visto que os erros de formatação são descobertos nas verificações referidas anteriormente, os erros que podem surgir nesta fase estão relacionados com a falha do cumprimento das regras estabelecidas na criação da base de dados.
Desta maneira, após o comando ser processado e ser retornado um CommandResult para a aplicação, verifica-se o valor do booleano _success_ e é apresentada a mensagem de erro caso necessário.

## Avaliação crítica

(_enumerate the functionality that is not concluded and the identified defects_)

(_identify improvements to be made on the next phase_)
