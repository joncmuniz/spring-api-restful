# Requirements:
- Java  8+
- Maven 3.6
- Docker version 18.06.3-ce+
- Postman

Este projeto é um exercício de funcionalidades, com o intuito de aprendizado, para adoção de boas práticas e técnicas na implementação de de Apis Restful.

# 1 - Implantação containers docker e executação de testes pelo PostMan

**1.1 Criar rede no docker:**
- docker network create --driver=bridge  --subnet=172.28.0.0/16 --ip-range=172.28.5.0/24 --gateway=172.28.5.254 postgres-spring-api-compose-network

**1.2 Criar ambiente a partir de conteiners ja existentes no docker hub**

**1.2.1 Criar container do banco de dados com o BD api ja criado.**
-	No diretorio docker/database/postgres-api - executar o comando: **docker-compose up -d**

**1.2.2 Criar container do binario já disponibilizando o serviço na porta 8082** 
- No diretorio docker/api	executar o comando: **docker-compose up -d**

**1.3 Documentação das apis é disponibilizada na url: http://localhost:8082/api/swagger-ui.html**

**1.4 A coleção de exemplos de execução de apis via postman estão disponibilizadas no diretorio postman**
- Microservices-collection.postman_collection.json - **A coleção não contem testes, apenas a execução das Apis**


# 2 Mapeamento de funcionalidades disponibilizadas:
**2.1 Como criar novos containers?**

Este item tem como objetivo demonstrar funcionalidades de como criar as imagens base:
- Em relação ao banco de dados ao criar uma imagem:
    - é usado o **docker/database/postgres-create-image/docker-compose.yml** que faz referencia ao Dockerfile **docker/database/postgres-create-image/build-postgres/Dockerfile** docker e scripts **docker/database/postgres-create-image/build-postgres/init-user-db.sh** que vão ser exectudos na inicialização criando neste caso o banco de dados - api;
- Em relação ao binario java:
    - o primeiro passo é criar o binario que vai ser disponibilizado no diretorio **api/target**, para isso na raiz do projeto execute o comando **mvn clean install**, apos, executar na raiz do projeto o comando: **docker-compose up -d**, que está apontando para o Dockerfile, tambem na raiz do projeto.

**2.2 Estrutura do projeto**
 
 **O projeto é dividido em modulos**

**common**
- Estrutura base para camada de serviços, Interfaces, execeções;
- Estrura base para camada de acesso a dados, Interfaces, utilitarios(paginão, ordenação), filtros - Specification
- Estrutura base para camada de controle,  

**api-client**
- Modelos de entidade
- Contexto da camada persistencia

**api**
- Implementação da estrutura baseada no commons e api-client;
- Modulo responsavel por agregar as denpendencias e realizar startup;
- Implementação dos controllers;
- Implementação dos serviços;
- Implementação dos daos;
- Conversões de execções - entre a camada de serviços e controller;

# Observações
A ideia é fornecer uma estrutura base para criação de controles, serviços e camada de persistência.

Com isso é fornecido um conjunto de operações básicas, comuns a todo tipo CRUD, além de permitir operações específicas que estão fora desse contexto.

As operações base podem ser analisadas no fluxo das operações do Customer 
- Iniciando nos controles 
  - br.com.joncmuniz.microservices.api.web.controller.CustomersController 
  - br.com.joncmuniz.microservices.api.web.hateoas.CustomerHateoasController
  
**Operações base:**

- findOne
- findAllfindAllSorted
- findAllPaginated
- findAllPaginatedAndSorted
- create
- update
- delete
- deleteAll
- count

**Operações fora do contexto generico**

A relação entre Customers e Adresses não é atendido por esse estrutura base, forçando assim o uso de serviços que tratem essa relação espcificamente. 

Poder ser mapeado a partir do controler 

- br.com.joncmuniz.microservices.api.web.hateoas.AddressHateoasController

**Como dito no item 1.3 é possivel mapear as operações disponibilizadas**

**Resumo de Operações do controler:**

- GET /customers - Retorna um lista de  customers
- GET /customers/12 - Retorna um cliente epsecifico
- POST /customers - Cria um novo cliente
- PUT /customers/12 - Atualiza um cliente
- DELETE /customers/12 - Deleta um cliente #12

Endpoints de relação entre Clientes e Endereços

- GET /customers/12/adresses - Retorna lista de adresses para cliente #12
- GET /customers/12/adresses/5 - Retorna endereço #5 para cliente #12
- POST /customers/12/adresses - Cria um novo endereço para o cliente #12
- PUT /customers/12/adresses/5 - Atualiza endreço #5 para cliente #12
- DELETE /customers/12/adresses/5 - Deleta endereço #5 para cliente #12

# Testes com Junit
No projeto é demostrado 3 tipos de testes com junit
- Teste unitario do modelo Customer, neste teste é verficado a funcionalidade do atributo transiente - age da entidade Customer. Alem disso é demonstrado como realizar testes com geração de dados dinamicos que vão ser passados ao metodo de teste. Obtendo assim varios reports distintos para cada instancia passado ao metodo, em outras palaras, um unico teste que valida diferentes tipos de contextos de Customers.
A ideia é utilizar o padrão de dataProvider, Apis usadas - @ParameterizedTest, @ArgumentsSource(ArgumentsLoaderProvider.class)
br.com.joncmuniz.microservices.api.client.persitence.model.CustomerTest

**Para os testes abaixo é necessário que o PostgreSQL esteja em execução**

- Teste de integração com acesso a dados, neste teste é validado a partir da execução de um dao, injetado no contexto do teste, se o mesmo é capaz realizar acesso aos DB e retornar o dado esperado. O detalhe deste teste é a integração com a api DBUnit o que facilita a criação de cenarios esperados na base de dados, como por exemplo, utilizando DataSets mapeados no metodos dos testes @DatabaseSetup("classpath:createCustomer.xml") - Data set que garente que tenha a instacia esperada na tabela de customers. 
CustomerRepositoryIntegrationTest

**O teste abaixo, apesar de ser com contexto Web, não entra em conflito caso tenha alguma instância em execução da API, pois o mesmo cria o seu contexto com uma porta randômica, evitando assim qualquer problema de portas indisponíveis. necessitam de ter o PostgreSQL em execução**

- Teste de integração da camada de controler ao DAO, neste teste é integrado Persistencia de dados via DBUnit, TestRestTemplate Api, WebEnvironment. É validado o contexto web de retorno, corpo, codigos Http, existência de endpoints.
CustomerHateoasControllerTest

# Tratamento de execeções

**Foi criado um ControllerAdvice com o intuito de se fazer a intercpção de Exceções de negócio, erros e fazendo um de/para em relação aos erros HTTP. Com o intuito de demonstrar ações de tratamento de exceções com estruturas mais desacopladas**

- RestResponseEntityExceptionHandler

# Tratatamento de ações através de Publicações de Eventos

**Existe uma estrutura no projeto onde foram definidos 4 tipos de eventos:**

- AfterResourceCreatedEvent
- MultipleResourcesRetrievedEvent
- PaginatedResultsRetrievedEvent
- SingleResourceRetrievedEvent

**Como exemplo, é demonstrado no uso dos endpoints customers-controller, onde para cada operação é lançado o evento específico e no listerner é adicionado no header o link referente ao contexto da operação**

# Filtros de queries específicos com org.springframework.data.jpa.domain.Specification<T>

- O exemplo é demonstrado para os endpoints (mais especificamente no service **package br.com.joncmuniz.microservices.api.service.impl.AddresseServiceImpl**):
  - get    - /hateoas/customers/{customerId}/adresses             - getAdressesForCustomer - 
  - delete - /hateoas/customers/{customerId}/adresses/{addressId} - delete
