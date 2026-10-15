# InovaGAB

Aplicação desenvolvida para o desafio do Grupo Águia Branca, com foco em
gestão de estratégias, ideias e projetos de inovação.

## Tecnologias

**Backend:** Java 21, Spring Boot 4.1.1, Spring Data MongoDB, Spring
Security, JWT, BCrypt, Maven, MongoDB Atlas e Google Gemini API.

**Android:** Kotlin, Jetpack Compose, Retrofit, Gson e Android Studio.

## Pré-requisitos

-   JDK 21 ou superior
-   IntelliJ IDEA
-   Android Studio
-   MongoDB Atlas
-   Chave da API do Google Gemini para utilizar as funcionalidades de IA
-   Emulador Android ou dispositivo físico

## Executando o Backend

### 1. Abrir o projeto

Abra a pasta do backend no IntelliJ IDEA e aguarde o Maven carregar as
dependências.

### 2. Configurar o MongoDB

No arquivo `src/main/resources/application.properties`, configure:

``` properties
spring.application.name=inovagab
spring.mongodb.uri=mongodb+srv://USUARIO:SENHA@SEU-CLUSTER.mongodb.net/inovagab?appName=Cluster0
spring.mongodb.database=inovagab
server.port=8080
```

Substitua os dados pelos dados da sua conexão do MongoDB Atlas.

### 3. Configurar o JWT

No mesmo arquivo:

``` properties
jwt.secret=SUA_CHAVE_SECRETA
jwt.expiration-ms=86400000
```

### 4. Configurar o Gemini

Para utilizar a análise de ideias por IA:

``` properties
gemini.api.key=SUA_CHAVE_DO_GEMINI
gemini.model=gemini-3.6-flash
```

Não compartilhe as chaves em repositórios públicos.

### 5. Iniciar o backend

Execute a classe `InovagabApplication` pelo IntelliJ.

O servidor será iniciado em:

`http://localhost:8080`

Se o projeto possuir o Maven Wrapper, também pode ser executado com:

``` powershell
.\mvnw.cmd spring-boot:run
```

## Executando o Android

1.  Abra a parte Android no Android Studio.
2.  Inicie o backend antes do aplicativo.
3.  Para o emulador Android, utilize a URL:

``` text
http://10.0.2.2:8080/
```

4.  Execute o aplicativo pelo Android Studio.
5.  Faça login com um usuário cadastrado no backend.

Em um dispositivo físico, substitua `10.0.2.2` pelo IP da máquina que
está executando o backend.

## Fluxo da aplicação

``` text
Android
   ↓
Retrofit
   ↓
Spring Boot REST API
   ↓
Spring Security / JWT
   ↓
Controllers
   ↓
Services
   ↓
Repositories
   ↓
MongoDB Atlas
```

Fluxo da IA:

``` text
Aplicação/Postman
      ↓
Spring Boot
      ↓
IAService
      ↓
Google Gemini API
      ↓
Análise da ideia
      ↓
MongoDB
```

## Perfis de acesso

-   **OPERADOR:** cadastro e consulta das próprias ideias.
-   **GESTOR:** gerenciamento de ideias, projetos, resultados e análise
    de ideias com IA.
-   **LIDER:** consulta de estratégias, projetos, resultados, dashboard,
    ranking e auditorias.

As permissões são controladas pelo Spring Security utilizando JWT.

## Principais funcionalidades

-   Cadastro e login de usuários
-   Autenticação com JWT
-   Controle de acesso por perfil
-   Estratégias de inovação
-   Cadastro e gerenciamento de ideias
-   Priorização e aprovação de ideias
-   Projetos e acompanhamento de progresso
-   Registro de resultados
-   Dashboard de indicadores
-   Ranking de ideias analisadas
-   Análise de ideias com Google Gemini
-   Auditoria de ações

## Principais endpoints

``` text
/auth
/api/usuarios
/api/estrategias
/api/ideias
/api/projetos
/api/resultados
/api/dashboard
/api/ia
/api/auditorias
```

As rotas protegidas exigem um token JWT no formato:

``` text
Authorization: Bearer SEU_TOKEN
```

Exemplo de análise de uma ideia:

``` http
POST /api/ia/ideias/{ideiaId}/analisar
```

Exemplo de ranking:

``` http
GET /api/ia/ranking
```

Exemplo de auditorias:

``` http
GET /api/auditorias
```

## Banco de dados

O projeto utiliza MongoDB Atlas como banco de dados NoSQL.

Principais collections:

``` text
usuarios
estrategias
ideias
projetos
resultados
auditorias
```

Os dados gerados pela análise de IA são armazenados na própria
collection `ideias`.

## Observações

-   O backend deve estar em execução antes do aplicativo Android.
-   O MongoDB Atlas precisa estar acessível para o backend.
-   As chaves do MongoDB, JWT e Gemini são privadas.
-   No emulador Android, `10.0.2.2` representa a máquina que está
    executando o backend.
