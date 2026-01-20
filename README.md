# API de Veículos ? Desafio Técnico Tinnova

Este projeto foi desenvolvido como parte do **desafio técnico da Tinnova**, utilizando **Java 17**, **Spring Boot 3**, **JWT**, **OpenAPI**, **JUnit/Mockito** e aplicando princípios de **SOLID** e **Clean Code**.

---

## Objetivo

Disponibilizar uma API REST para gerenciamento de veículos, com:

* CRUD completo
* Filtros avançados
* Relatórios
* Autenticação e autorização via JWT
* Documentação OpenAPI (Swagger)
* Testes automatizados com cobertura ? 75%

---

## Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* Spring Web
* Spring Data JPA
* Spring Security (JWT)
* H2 Database
* Redis (cache)
* OpenAPI 3 (Swagger)
* JUnit 5
* Mockito
* JaCoCo
* Docker (Redis)

---

## Arquitetura

O projeto segue uma arquitetura em camadas:

```
controller  ?  service  ?  repository
                 ?
               domain
```

Principais conceitos aplicados:

* SOLID
* Clean Code
* DTOs para entrada e saída
* Soft Delete
* Separação de responsabilidades

---

## Segurança (JWT)

* Autenticação via `/auth/login`
* Autorização baseada em roles:

    * `USER`: leitura
    * `ADMIN`: escrita (POST, PUT, PATCH, DELETE)

O token JWT deve ser enviado no header:

```
Authorization: Bearer <token>
```

---

## Documentação da API (Swagger)

A API é totalmente documentada com **OpenAPI 3**.

### Acesso

```
http://localhost:8080/swagger-ui.html
```

---

### Prints do Swagger

#### Tela inicial da documentação

![Swagger Home](docs/swagger/swagger-home.png)

#### Endpoint de Login (JWT)

![Swagger Login](docs/swagger/swagger-login.png)

#### Cadastro de Veículo (ADMIN)

![Swagger Post Veiculo](docs/swagger/swagger-post-veiculo.png)

#### Relatório por Marca

![Swagger Relatorio](docs/swagger/swagger-relatorio-marca.png)

 **Observação:**
> Os arquivos de imagem devem ser colocados em:
>
> ```
> docs/swagger/
> ```

---

## Endpoints Principais

### Veículos

* `GET /veiculos`
* `GET /veiculos/{id}`
* `POST /veiculos` (ADMIN)
* `PUT /veiculos/{id}` (ADMIN)
* `PATCH /veiculos/{id}` (ADMIN)
* `DELETE /veiculos/{id}` (ADMIN ? soft delete)
* `GET /veiculos/relatorios/por-marca`

### Autenticação

* `POST /auth/login`

---

## Testes e Cobertura

* Testes unitários
* Testes de controller
* Testes de integração com JWT
* Banco H2 em memória
* Cobertura com **JaCoCo ? 75%**

### Executar testes

```bash
mvn clean verify
```

### Relatório de cobertura

```
target/site/jacoco/index.html
```

---

## Redis (Cache)

O Redis é utilizado para cache de dados externos (ex: cotação do dólar).

### Subir Redis com Docker

```bash
docker run -d --name redis -p 6379:6379 redis:7-alpine
```

---

## Executando o Projeto

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

## Autor

**Diego Cunha**
Analista Desenvolvedor Java

Projeto desenvolvido com foco em boas práticas, clareza de código e padrões exigidos em ambientes corporativos.

---

## Status do Projeto

? Desafio técnico finalizado
? Todos os requisitos atendidos
? Pronto para avaliação
