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
Um CommandResult é responsável pelo armazenamento dos resultados obtidos de um comando. Esta classe conta com a presença de dois campos:
* boolean success                : Determina se o comando executado foi concluído com sucesso ou não;
* LinkedList\<Entity> results    : Os resultados em si.

A aplicação encontra-se desenvolvida de maneira a apresentar os resultados um a um, obtendo sempre a View correspondente a Entity atual para realizar a apresentação da mesma. No caso do comando não ter sido concluído com sucesso, a aplicação apresenta uma mensagem, dizendo que ocorreu um erro durante a execução do comando. O preenchimento de um objeto deste tipo é responsabilidade de cada CommandHandler.

### Entities
De forma a representar cada tipo de resultado, foi realizada a *interface* Entity, que servirá de base para, por exemplo, todas as concretizações das linhas das tabelas da base de dados em classes, onde cada campo vai corresponder a um atributo.
A *interface* Entity tem as seguintes componentes:
* enum EntityType               : Um enumerado que contém o nome de cada uma das entidades na base de dados;
* EntityType getEntityType()    : Uma função, a ser implementada por cada uma das classes, que irá retornar o tipo da entidade.
De forma semelhante, existem concretizações de cada uma das tabelas da base de dados sob a forma de Views.

#### CommandRequest
Um CommandRequest é responsável pela passagem de informação de contexto a um CommandHandler, de maneira a possibilitar a execução do comando. Para isto, esta classe conta com os seguintes campos:
* Path path                                     : Especifica o caminho do pedido, que pode ou não conter variáveis;
* Parameters params                             : Armazena os parâmetros passados;
* TransactionManager trans       : Responsável por efetuar a conexão a uma base de dados, e efetuar uma transação;
* Iterator<Pair<String, String>> commands : Iterador dos nomes e descrições dos vários comandos da aplicação. 

Todos este campos podem ser acedidos através do seu respetivo *getter*, de maneira a ser possível aceder à informação por eles armazenada. Abaixo encontram-se descritas as classes respetivas a este campos, nomeadamente como chegar à informação relevante ao CommandHandler.

#### TransactionManager
A classe TransactionManager é a responsável por efetuar uma conexão a um servidor PSQL, e executar uma transação no mesmo. Para isto, esta classe contém o campo *connectionUrl*, que armazena o URL completo da base de dados. A classe App é a responsável pela obtenção do valor deste campo, que deverá estar armazenado numa variável de ambiente do sistema operativo.

Para efetuar uma transação, é utilizado o método *executeTransaction()*, que recebe uma interface funcional SqlFunction como argumento. Esta interface contém um método que descreve todo o processo da transação, o qual deverá ser implementado pelos CommandHandlers. O método de execução da transação em TransactionManager começa por efetuar uma ligação à base de dados, e tenta depois executar o método da interface. Caso este seja concluído com sucesso, executa-se um *commit* da transação. Caso contrário, faz-se um *rollback* da mesma. Independentemente do resultado, a conexão estabelecida é sempre fechada. O valor de retorno da função identifica o sucesso da transação.

#### Parameters
A classe Parameters é responsável pelo processamento de parâmetros de um dado comando. Por esta razão, é instanciado um objeto deste tipo sempre que é instanciado um CommandRequest. Com esta classe, a informação sobre os parâmetros fica sintetizada e de fácil acesso para as outras classes que necessitem de consular os mesmos.

A classe Parameters contém apenas um campo:
* HashMap<String, LinkedList\<String>> params : Armazena o nome dos parâmetros e os seus valores.
    
Para resumir a informação dos parâmetros, utilizámos um HashMap para armazenar o valor dos mesmos. Esta estrutura de dados utiliza como chave o nome do parâmetro, e como valor apresenta uma lista com os respetivos valores. É utilizado uma lista para armazenar os valores de dado parâmetro de forma a permitir a atribuição de vários valores ao mesmo parâmetro.

No construtor de Parameters é chamado um método privado que se responsabiliza pelo preenchimento do HashMap. Este mesmo método é responsável pela deteção de erros de escrita/formatação na String que contém os parametros. Caso sejam detetados erros, será lançada uma exceção.

De modo a facilitar o acesso aos valores dos parametros, esta classe disponibiliza dois métodos de acesso a dados:
* String getValue(String key)               : Retorna o primeiro valor da lista associada a um parâmetro (facilita o acesso a parâmetros com um único valor);
* Iterable\<String> getValues(String key)   : Retorna um iterável que permite percorrer os vários valores associados a dado parâmetro.

#### Headers
A classe Headers é responsável pelo processamento dos headers de um dado comando. Como, nesta fase, os comandos em si não necessitam dos headers, logo não é necessário passar um objeto deste tipo ao CommandRequest.

A classe Headers contém apenas um campo:
* HashMap<String, LinkedList\<String>> headers : Armazena o nome dos headers e os seus valores.

A classe App vai-se encarregar de definir a stream de output consoante a existência do header `file-name`. Já o header `accept` é passado como parâmetro à classe View, de maneira a esta apresentar os resultados com base no valor do mesmo (html ou texto simples).

#### Paths
Para armazenar informações sobre caminhos, foi decidido separar a informação relativa a estes em classes distintas. Com isto, dá-se a existência de uma classe com informação sobre uma diretoria, uma classe com informação sobre um dado caminho, e uma classe com informação sobre um *template* de uma diretoria. Abaixo estão descritas estas mesmas classes.

##### Directory
Um Directory representa uma diretoria, ou seja, uma secção de um caminho (por exemplo, quando se executa o comando _GET /rooms_, rooms é considerado um Directory), logo, trata-se de uma classe simples, composta por dois campos:

* String name           : Indica o nome da diretória. Caso seja uma variável, este nome será encurtado com o propósito de remover os parêntesis curvos;
* boolean isVariable    : Determina se a secção é ou não uma variável (por exemplo, {rid}).

Ambos os campos são obtidos através do respetivo *getter*.

##### BasePath
Devido às semelhanças existentes entre um caminho e um *template* de um caminho, foi criada a classe abstrata BasePath, cujo propósito é juntar todas estas semelhanças numa só classe, de maneira a evitar repetição de código no projeto.

Esta classe contém apenas um único campo:
LinkedList\<Directory> path  : Armazena um caminho como uma lista de diretorias.

De maneira a preencher a lista acima listada, existe o método *parsePath(String path)*, que recebe uma String que representa um caminho, e preenche a lista chamando o método *addDirectory(String dir)* para cada diretoria válida. Este último método terá de ser implementado pelas classes específicas. Caso o caminho passado não seja válido (verificação através do método *isValid(String path)*), o método lança uma exceção.

##### Path
A classe Path estende a classe BasePath, descrita anteriormente, e tem a função de armazenar informação relativa a um caminho. Nesta classe, existe um novo campo que é específico a caminhos concretos:

HashMap<String, String> variables : Armazena o nome de uma variável, assim como o valor da mesma.

Foi utilizado um HashMap para armazenar as variáveis presentes num caminho. A razão por esta escolha deve-se ao facto de que uma variável tem por norma um nome único dentro do mesmo caminho, e desta forma é possível efetuar o acesso ao valor da mesma através do nome dela. Para efetuar o acesso a uma variável, basta passar o nome desta ao método *getVariable(String varName)*.

Para preencher este HashMap, tem-se o método *addVariable(String varName, String var)*. A classe responsável por chamar este método é a classe PathTemplate, que se encontra descrita abaixo.

O método *addDirectory(String dir)* assume sempre que o caminho a adicionar não é variável, pois não se consegue obter esta informação neste contexto.

##### PathTemplate
A classe PathTemplate armazena informação relativa a *templates* de caminhos (caminhos codificados desta forma: /dir1/{var1}/dir2 ...). Esta classe não contém nenhum campo específico, sendo que apenas contém o campo da superclasse. Em termos de métodos, tem-se:

* boolean isTemplateOf(Path path) : Verifica se a instância atual é *template* de uma instância de Path. Usado durante o encaminhamento de comandos;
* void applyTemplate(Path path)   : Preenche a lista de variáveis de uma instância de Path com a informação da *template*. Necessário pois não é possível saber quais as diretorias variáveis apenas com a informação do caminho concreto;
* boolean isVariable(String dir)  : Verifica se uma diretoria é variável ou não, com base na sua representação em String. Usado durante a adição de uma diretoria à campo *path*.

### Encaminhamento dos comandos
Para efetuar o encaminhamento dos vários comandos, existe a classe Router. Esta classe permite que, através da passagem de um método e de um caminho, seja retornado um CommandHandler associado aos parâmetros passados.

Para o efeito, foi utilizada como estrutura de suporte, uma árvore n-ária, representada pela classe NTree na package *utils*. Esta árvore é composta por 2 níveis:

Nível 1 : Composto pelos vários Métodos, representados pela classe MethodNode
Nível 2 : Composto pelas PathTemplates e respetivos Handlers, representados pela classe HandlerNode

A classe da árvore n-ária encontra-se descrita em baixo, assim como a informação dos vários passos do encaminhamento dos comandos.

#### NTree
A classe NTree representa uma árvore n-ária. Esta classe conta com a presença de apenas um campo:

HashMap<Method, MethodNode> methods  : Armazena os nós dos vários métodos presentes na árvore.

A razão pela implementação do primeiro nível da árvore em forma de um HashMap deve-se ao alto desempenho na procura de um nó associado a um dado método.

De maneira a adicionar uma nova entrada à àrvore, existe o método *add(Method method, PathTemplate template, CommandHandler cmd)*, que adiciona um novo nó de método à árvore (se necessário), e chama o método *addHandler(template, cmd)* desse mesmo nó (método este que se encontra descrito abaixo).

De maneira a ser possível percorrer todos os comandos existentes na aplicação, a classe NTree implementa a interface Iterable<Pair<String, String>>, sendo que cada objeto do tipo Pair armazena o nome (método + template do Path) e descrição de cada comando existente. Estas descrições encontram-se armazenadas nas classes de cada handler, como forma de um método *toString()*.

Para possibilitar o acesso deste iterador aos handlers que necessitam dele (por exemplo, o OptionsCommand), este é passado dentro de um objeto do tipo CommandRequest, através da chamada ao método *getCommands* do Router.

#### MethodNode
A classe MethodNode é a representação em nó de um Method. Representa o primeiro nível da NTree. Esta classe contém apenas um campo:
* HashSet\<HandlerNode> cmdhandlers    : Contém todos os Handlers associados a este método.

A utilização de um HashSet tem como utilidade evitar nós duplicados. Este HashSet será depois iterado no método *getHandlerAndApplyTemplate(Path path)*, que irá procurar pelo template correto do Path em questão, e retornar o CommandHandler associado ao HandlerNode em análise.

#### HandlerNode
HandlerNode é uma classe simples cuja função é armazenar um PathTemplate e um CommandHandler. Esta classe encontra-se associada a um MethodNode, sendo que será o último nível da NTree.

Para além dos campos armazenados, e do *getter* do *handler*, esta classe contém o seguinte método:

* boolean checkTemplateAndApply(Path path)    : Verifica se o PathTemplate presente na instância atual é *template* do Path passado como parâmetro. Caso o seja, aplica a *template* ao Path, e retorna *true*. Caso contrário, retorna *false*.

#### Preenchimento da árvore
O preenchimento da árvore n-ária é feito no arranque da aplicação, no método *App.addCommands(Router router)*. Este método chama, para cada comando existente, o método *addRoute(Method method, PathTemplate path, CommandHandler handler)* do Router correspondente, que por sua vez chama o método *add(Method method, PathTemplate template, CommandHandler cmd)* da NTree. Este método encontra-se descrito acima.

#### Obtenção de um Handler
O processo de obtenção de um Handler é realizado pelo Router. Este processo é iniciado durante a execução de um comando, ou seja, dentro do método *executeCommand(String[] args)* de App. Este método chama então o *findRoute(Method method, Path path)* do Router, que por sua vez chama o método *getHandlerAndApplyTemplate(Method method, Path path)* da NTree.

Este último irá obter um MethodNode do seu HashMap, utilizando como chave o Method passado como argumento. Após essa obtenção, este método irá chamar um outro método, que se encontra presente no MethodNode obtido (*getHandlerAndApplyTemplate(Path path)*). Este método irá então percorrer o HashSet de HandlerNodes, verificando qual destes é que contém o PathTemplate correspondente ao Path passado como argumento e, quando este for encontrado, aplicará a *template* ao Path, e retornará o CommandHandler correspondente.

### Apresentação de Resultados

Para a representação dos resultados, foi decidido separar a secção visual do resto das outras classes, com o objetivo de conter tudo o que se encarrega com apresentação de resultados num único *package*.
Assim sendo, foi concebida a classe abstrata View, cujas concretizações vão ser as representações visuais dos vários resultados.
Esta classe conta com dois campos:

* Iterable\<Entity> entities - Contém as várias entidades resultantes da execução de um comando.
* Entity entity - Armazena apenas a primeira entidade do conjunto de entidades recebidas. Usado para views que contam com apenas uma entidade.

Para além disto, a classe base contém também um método estático designado por *getInstance*, cujo objetivo é fornecer a concretização adequada à entidade fornecida. O tipo de entidade verificado é o primeiro do conjunto recebido, o que é válido por agora devido à não existência de comandos que resultem em duas (ou mais) entidades distintas. Existem também o método de instância *display*, que recebe uma *stream* de *output* e o formato de visualização que se pretende utilizar. Este método é responsável pelo chamamento de um dos métodos específicos de *display* (*displayHtml* ou *displayText*), assim como pela escrita na *output stream* pretendida. Estes últimos métodos terão de ser implementados pelas várias concretizações de *View*, sendo estas específicas para cada uma das entidades existentes.

#### Representação em HTML

Para efetuar a representação dos resultados no formato HTML, foi decidido criar uma domain-specific language (DSL) para que o código se mantenha simples e fácil de entender.

Como ponto de partida, foi concebida a classe abstrata *Element*, sendo esta a classe de onde todos os outros elementos de HTML vão derivar (Head, Html, Table, ...).
Esta classe conta com os seguintes campos:
* LinkedList\<Element> children - contém todos os filhos do elemento atual, visto que a linguagem HTML se assemelha a uma árvore, em que cada elemento pode ter N outros elementos dentro do mesmo.
* Pair\<String, String> delimiters - delimitadores do elemento atual, pois todos os elementos (excluíndo algumas exceções raras, como o break) da linguagem HTML têm delimitadores de ínicio e de fim (exemplo para o H1: `<h1> </h1>`)

Esta classe conta também com a presença de um método *toString()*, que retorna a representação de um elemento em String, ou seja, o seu código HTML, com a devida indentação.

Foi também criada a classe ElementText, que estende de Element, cujo objetivo é refletir elementos de HTML terminadores, ou seja, elementos que contêm apenas texto e não outros elementos, como, por exemplo, os elementos *Header* e *Paragraph*.

Para aumentar a legibilidade do código e evitar a constante criação de objetos usando o operador *new*, foi concebida a classe *HTMLDsl*, constituída apenas por métodos estáticos que correspondem a cada uma das classes que foram criadas. Desta forma, para utilizar a DSL, basta importar esta última classe e utilizar os métodos estáticos da mesma.

### Gestão de ligações

Para cada *CommandHandler* é necessário estabelecer uma conexão com a base de dados de modo a possibilitar a consulta e inserção de dados. Para isso, foi criada a classe *TransactionManager* descrita anteriormente. Esta classe recebe no seu construtor o URL para estabelecer uma conexão com dada base de dados.

Visto que as conexões apenas são estabelecidas e utilizadas dentro dos *CommandHandlers*, e por sua vez os *CommandHandlers* recebem um *CommandRequest* por parâmetro, dá-se que os objetos do tipo *TransactionManager* são instanciados dentro de *CommandRequest*. Desta forma, é possível criar a ligação com a base de dados e interagir com a mesma dentro dos *handlers* dos comandos.

Como referido anteriormente, *TransactionManager* apenas dispõe de um método que executa uma transação. A conexão efetuada neste método é estabelecida com *AutoCommit* a *false*. Desta forma, só serão feitas mudanças à base de dados caso seja explicitamente chamado o método *commit()* para dada *Connection*.

Dadas estas caraterísticas, dentro de todos os *CommandHandlers* é chamado o método *executeTransaction()*, passando-lhe como parâmetro uma função que descreve a transação a ser efetuada. Esta transação é realizada dentro de um bloco *try-catch-finally* em TransactionManager. Através de um *catch (Exception e)*, são garantidos os procedimentos necessários no caso de ocorrência de erros, nomeadamente o *rollback* dos possíveis valores inseridos na base de dados. Caso não haja nenhuma exceção detetada na execução dos comandos SQL, é feito um *commit* aos dados.

### Acesso a dados

Cada um dos comandos está refletido numa classe com o sufixo Command, todas elas implementam a interface CommandHandler. Nos nomes das classes também é possível encontrar o Method, que é utilizado como prefixo.
Dentro do mesmo Method, os handlers são semelhantes, sendo assim, basta explicar de forma geral como é que cada um opera. É importante realçar que em todos os comandos (à exceção do EXIT) é efetuada uma conexão à base de dados.
* EXIT - É retornado null para que a App se encarregue de terminar a aplicação.
* GET - São realizadas queries à base de dados, utilizando o path para sabermos quais são as tabelas, e, em alguns casos, um parâmetro, para obter resultados específicos. O resultado da query é refletido num ResultSet, que irá ser iterado, colocando a informação nele armazenada num CommandResult, para apresentar ao utilizador no final da execução. Todas as queries presentes nestes comandos seguem uma estrutura simples, em alguns casos sendo necessário um _WHERE_, como por exemplo quando se quer obter um _room_ através do seu _rid_.
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

À medida que se desenvolveu o projeto, demos conta de alguns defeitos que podem vir a prejudicar o desenvolvimento (nomeadamente no que toca à complexidade temporal, e no acesso à base de dados).
Entre eles achamos pertinente destacar:
* Repetição de código em todos os Handlers, para efetuar o acesso à base de dados;
* Algoritmo de procura de HandlerNodes não revela ser o mais eficiente;

Para a próxima fase podemos melhorar os aspetos referidos na lista anterior, tanto como há sempre a possibilidade de encontrarmos mais defeitos e necessitarmos de soluções para os mesmos. É possível que o código não esteja completamente legível, ou com falta de comentários, no entanto, isso é um esforço que tem de ser feito ao longo do desenvolvimento. Dito isto, esperemos melhorar na próxima fase em todas as vertentes.
