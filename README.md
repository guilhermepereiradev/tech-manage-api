# TechManage

TechManage é uma aplicação para gerenciar usuários.

## Tecnologias Usadas

- **Java 21**
- **Spring Web**
- **Spring Data JPA**
- **Spring Validation**
- **PostgreSQL**
- **JUnit, Mockito, REST Assured e H2 para testes**


## Pré-requisitos

- [JDK 21](https://adoptium.net/temurin/releases/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Docker](https://www.docker.com/get-started) (Opcional)

## Banco de Dados

1. Scripts de migração e dados de testes estão disponiveis em [src/main/resources/db](https://github.com/guilhermepereiradev/tech-manage-api/tree/main/src/main/resources/db). (Executados automaticamente pelo Flyway).

## Como Rodar a Aplicação
1. Clone o repositório:
   ```bash
   git clone git@github.com:guilhermepereiradev/tech-manage-api.git
   ```
### Executando com Docker
1. No diretório raiz da aplicação:
   ```bash
   docker compose up
   ```

### Executando com Maven
1. No diretório raiz da aplicação, rode o comando maven com as variáveis de ambiente do DB (ou altere diretamente no arquivo `application-dev.yaml`):
   ```bash
    ./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev --DB_HOST={db_host} --DB_PORT={port} --DB_NAME={db_name} --DB_USER={user} --DB_PASSWORD={password}"
   ```

#### A aplicação estará disponível em http://localhost:8080 por padrão.


## Testes
Para rodar os testes, execute os seguintes comando:

1. Testes de unidade:
   ```bash
    ./mvnw test
   ```
2. Testes de integração:
   ```bash
     ./mvnw test -Dtest='**IT' 
   ```

## Endpoints
#### A documentação da API está disponível pela interface do [swagger](http://localhost:8080/swagger-ui/index.html).

### `GET` /api/users
   - Descrição:
      - Lista todos usuários.
   - Response
      - HTTP status: `200 OK`.
      ```json
      [
         {
            "id": 1,
            "fullName": "John Doe",
            "email": "johndoe@email.com",
            "phone": "+55 11 99999-9999",
            "birthDate": "2000-09-15",
            "userType": "ADMIN"
         },
         ...
      ]
      ```
### `GET` /api/users/{id}
- Descrição:
   - Busca um usuário por ID.
- Request
   - `{id}` ID do usuário desejado.
- Response
   - HTTP status: `200 OK`.
   ```json
   {
    "id": 1,
    "fullName": "John Doe",
    "email": "johndoe@email.com",
    "phone": "+55 11 99999-9990",
    "birthDate": "2000-09-15",
    "userType": "EDITOR"
   }
   ```

### `POST` /api/users
- Descrição:
   - Cria um novo usuário.
- Request
   - Corpo da requisição:
   ```json
   {
    "fullName": "John Doe",
    "email": "johndoe@email.com",
    "phone": "+55 11 99999-9990",
    "birthDate": "2000-09-15",
    "userType": "EDITOR"
   }
   ```
- Response
   - HTTP status: `201 CREATED`.
   ```json
   {
    "id": 4,
    "fullName": "John Doe",
    "email": "johndoe@email.com",
    "phone": "+55 11 99999-9990",
    "birthDate": "2000-09-15",
    "userType": "EDITOR"
   }
   ```
### `PUT` /api/users/{id}
- Descrição:
   - Atualiza um usuário existente.
- Request
   - `{id}` ID do usuário à ser editado.
   - Corpo da requisição:
   ```json
   {
    "fullName": "John Doe",
    "email": "johndoe12@email.com",
    "phone": "+55 11 99999-9990",
    "birthDate": "2000-09-15",
    "userType": "ADMIN"
   }
   ```
- Response
   - HTTP status: `200 OK`.
   ```json
   {
    "id": 4,
    "fullName": "John Doe",
    "email": "johndoe12@email.com",
    "phone": "+55 11 99999-9990",
    "birthDate": "2000-09-16",
    "userType": "ADMIN"
   }
   ```
### `DELETE` /api/users/{id}
- Descrição:
   - Remove um usuário por ID.
- Request
   - `{id}` ID do usuário desejado.
- Response
   - HTTP status: `204 NO CONTENT`.
