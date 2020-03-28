# Relatório técnico da Fase 1

## Introdução

Este documento contém os aspectos relevantes do desenho e implementação da fase 1 do projecto de LS.

## Modelação da base de dados

### Modelação conceptual ###

O seguinte diagrama apresenta a modelo entidade-associação para a informação gerida pelo sistema. 

![EA Diagram](https://github.com/isel-leic-ls/1920-2-LI42D-G08/blob/master/docs/images/EA_Diagram.png "EA Diagram")

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

De maneira a ser possível implementar vários tipos de comandos de um modo de uma maneira relativamente fácil, foi criada a interface CommandHandler. Esta interface é responsável por definir o "contrato" a ser cumprido por todos os *handlers* de comandos existentes. Esta interface é bastante simples, pois apenas contém um único método: o método *execute*. Será neste método que se encontra toda a lógica de um dado comando. De maneira a este se contextualizar, este método recebe como parâmetro um CommandRequest, cuja classe será descrita abaixo. Este método deverá, no final, retornar um objeto do tipo CommandResult, cuja classe será também descrita abaixo.

Em termos de classes, foram implementadas as seguintes classes, que serão utilizadas pelos *handlers*:

#### CommandResult
Um CommandResult é responsável pelo armazenamento dos resultados obtidos de um comando. Esta classe conta com a presença de três campos:
* boolean success               : Determina se o comando executado foi concluído com sucesso ou não;
* String title                  : O título dos resultados;
* LinkedList\<String> results    : Os resultados em si.

A aplicação encontra-se desenvolvida de maneira a apresentar os resultados linha a linha, começando por apresentar o título primeiro. No caso do comando não ter sido concluído com sucesso, a aplicação apresenta apenas o título, que deverá conter uma mensagem de erro correspondente. O preenchimento de um objeto deste tipo é responsabilidade de cada CommandHandler.

#### CommandRequest
Um CommandRequest é responsável pela passagem de informação de contexto a um CommandHandler, de maneira a possibilitar a execução do comando. Para isto, esta classe conta com os seguintes campos:
* Path path                                     : Especifica o caminho do pedido, que pode ou não conter variáveis;
* Parameters params                             : Armazena os parâmetros passados;
* PsqlConnectionHandler connectionHandler       : O *handler* responsável por efetuar a conexão a uma base de dados.

Todos este campos podem ser acedidos através do seu respetivo *getter*, de maneira a ser possível aceder à informação por eles armazenada. Abaixo encontram-se descritas as classes respetivas a este campos, nomeadamente como chegar à informação relevante ao CommandHandler.

#### Parameters
A classe Parameters é responsável pelo processo dos parâmetros em dado comando. Por esta razão, é instanciado um objeto do tipo Parameters sempre que é instanciado um CommandRequest. Com esta classe, a informação sobre os parâmetros fica sintetizada e de fácil acesso para as outras classes que necessitem de consular os parâmetros.

A classe Parameters apenas contém um campo:
* HashMap<String, LinkedList\<String>> params : Armazena o nome dos parâmetros e os seus valores.
    
Para resumir a informação dos parâmetros, utilizámos um HashMap para armazenar o conteúdo dos parâmetros. Esta estrutura de dados utiliza como chave o nome do parâmetro, e como valor apresenta uma lista com os respetivos valores. É utilizado uma lista para armazenar os valores de dado parâmetro de forma a conseguir atribuir vários valores ao mesmo parâmetro.

No construtor de Parameters é chamado um método privado que se responsabiliza pelo preenchimento do HashMap certificando-se que não houve erros na escrita dos parâmetros. Caso sejam detetados erros será lançada uma exceção.

Visto que o HashMap apresenta como valor uma lista, tem de suportar o acesso a parâmetros com apenas um valor e a parâmetros com mais do que um valor. Para isso, a classe dispõe de dois métodos públicos:
* String getValue(String key) : Retorna apenas o único valor associado a dado parâmetro;
* Iterable\<String> getValues(String key) : Retorna um iterável que permite percorrer os valores associados a dado parâmetro.

#### Paths

#### Directory
Um Directory representa uma secção de um Path (por exemplo, quando se executa o comando _GET /rooms_, rooms é considerado um directory), sendo assim, trata-se de uma classe simples, composta por dois campos:
* String name : Indica o nome da diretória. Caso seja uma variável, este nome sera encurtado com o propósito de remover as parêntesis curvas;
* boolean isVariable : Determina se a secção é ou não uma variável (por exemplo, {rid} );

Ambos os campos são obtidos através do respetivo *getter* tal como na maioria das outras classes.
##### BasePath


##### Path

##### PathTemplate

#### PsqlConnectionHandler
Esta classe é responsável por estabelecer uma conexão aos servidores, é necessário passar no construtor o _ip_, o _port_, o nome da base de dados, o nome do utilizador, e a password. Com a classe instanciada, é possível obter a Connection através do método getConnection, que se limita a conectar a um servidor com a informação passada no constutor.
Esta classe foi realizada com o intuito de se poderem estabelecer conexões a várias bases de dados, sendo que existe uma base de dados para testes, e uma para a execução da aplicação.


### Encaminhamento dos comandos

(_describe how the router works and how path parameters are extracted_)

### Gestão de ligações

(_describe how connections are created, used and disposed_, namely its relation with transaction scopes).

### Acesso a dados

Cada um dos comandos está refletido numa classe com o sufixo Command, todas elas implementam a interface CommandHandler. Nos nomes das classes também é possível encontrar o Method, que é utilizado como prefixo.
Dentro do mesmo Method, os handlers são semelhantes, sendo assim, basta explicar de forma geral como é que cada um opera. É importante realçar que em todos os comandos (à exeção do EXIT) é efetuada uma conexão à base de dados.
* EXIT - É retornado null para que a App se encarregue de terminar a aplicação.
* GET - São realizadas queries à base de dados, utilizando o path para sabermos quais são as tabelas, e, em alguns casos, um parâmetro, para obter resultados específicos. O resultado da query é refletido num ResultSet, que irá ser iterado, colocando a informação nele armazenada num CommandResult, para apresentar ao utilizador no final da execução. Todas as queries presentes nestes comandos seguem uma estrutura simples, em alguns casos sendo necessário um _WHERE_, como quando se quer obter um _room_ através do seu _rid_.
* POST - Em cada um destes comandos, o utilizador fornece sempre a informação que quer colocar na base de dados sob a forma de parâmetros. Assim sendo, todos os comandos consistem em _inserts_. É pertinente realçar que quando se instância o PreparedStatement, se fornece um parâmetro adicional, _Statement.RETURN\_GENERATED\_KEYS_, para que no ResultSet estejam presentes as chaves primárias que foram geradas através da auto-incrementação.

Por via do nosso modelo de base de dados, não existem nenhuns _statements_ em _SQL_ que consideramos não-triviais, assim sendo, não achamos pertinente realçar nenhum deles.

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
