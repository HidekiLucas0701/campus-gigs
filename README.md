# Campus Gigs

## INTEGRANTES

- ARTHUR GRACIANI RM561728
- GUSTAVO PINHEIRO RM566358
- LUCAS HIDEKI RM565355

O **Campus Gigs** é uma plataforma desenvolvida para facilitar a oferta e contratação de serviços dentro do ambiente acadêmico.

A proposta é conectar pessoas que possuem habilidades e desejam oferecer seus serviços com pessoas que procuram profissionais para realizar determinados trabalhos.

## 💡 Sobre o projeto

A plataforma permite que usuários se cadastrem, publiquem serviços e encontrem oportunidades oferecidas por outros usuários.

O projeto trabalha principalmente com três conceitos:

* **Usuários** — pessoas cadastradas na plataforma;
* **Serviços** — atividades oferecidas pelos usuários;
* **Contratações** — solicitações para contratar um serviço disponível.

A aplicação foi desenvolvida como uma **API REST**, responsável por centralizar as regras de negócio, autenticação e comunicação com o banco de dados.

## 🚀 Funcionalidades

* Cadastro de usuários;
* Autenticação e login;
* Autorização baseada em perfil;
* Publicação de serviços;
* Consulta de serviços disponíveis;
* Encerramento de serviços;
* Solicitação de contratação de serviços;
* Controle do status dos serviços e contratações.

## 🛠️ Tecnologias

* **Java 21**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Spring Security**
* **JWT**
* **PostgreSQL**
* **Flyway**
* **Bean Validation**
* **Lombok**
* **Gradle**
* **Docker / Docker Compose**
* **API Via CEP**

## 🏗️ Arquitetura

A aplicação possui uma estrutura organizada por responsabilidades, separando as camadas de apresentação, regras de negócio e persistência:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Os principais módulos da aplicação são:

```text
security
usuario
servico
contratacao
```

Essa organização facilita a manutenção e permite que novas funcionalidades sejam adicionadas sem concentrar toda a lógica em uma única camada.

## 🔐 Segurança

A autenticação da aplicação utiliza **JWT (JSON Web Token)**.

Após realizar o login, o usuário recebe um token que deve ser utilizado nas operações protegidas.

As senhas dos usuários são armazenadas utilizando **BCrypt**, evitando que sejam persistidas diretamente em texto puro.

A aplicação também possui controle de acesso baseado nos papéis:

* `USER`
* `ADMIN`

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL** como banco de dados relacional.

O controle da estrutura do banco é realizado pelo **Flyway**, permitindo que as alterações sejam versionadas através de migrations.

As principais entidades são:

* `Usuario`
* `Servico`
* `Contratacao`

Os relacionamentos entre elas permitem associar serviços aos seus prestadores e contratações aos usuários e serviços envolvidos.

## ▶️ Como executar

### Pré-requisitos

* Java 21
* Docker
* Docker Compose

### Executando o banco

```bash
docker compose up -d
```

### Executando a aplicação

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

As migrations do banco são executadas automaticamente durante a inicialização da aplicação.

---

# Autenticação

A API utiliza autenticação baseada em **JWT (JSON Web Token)**.

O fluxo de autenticação é:

1. Realizar o cadastro através de `POST /auth/register`.
2. Realizar o login através de `POST /auth/login`.
3. Obter o token JWT retornado pelo login.
4. Enviar o token no header `Authorization` das requisições protegidas.

---

## 1. Cadastro

### Endpoint

```http
POST /auth/register
```

### cURL

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Lucas Hideki",
    "email": "lucas@email.com",
    "senha": "123456"
  }'
```

> Os campos acima devem ser ajustados conforme o `RegisterDTO` utilizado pela aplicação.

### Resposta

Em caso de sucesso, o endpoint retorna os dados do usuário cadastrado.

Exemplo:

```json
{
  "id": 1,
  "nome": "Lucas Hideki",
  "email": "lucas@email.com"
}
```

O endpoint de cadastro é público e não necessita de um token JWT.

---

## 2. Login

Após realizar o cadastro, utilize o email e a senha para obter o token JWT.

### Endpoint

```http
POST /auth/login
```

### cURL

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "lucas@email.com",
    "senha": "123456"
  }'
```

### Resposta

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Copie o valor de `token` para utilizar nas requisições protegidas.

---

## 3. Atualização de usuário

O endpoint de atualização de usuário exige autenticação através de JWT.

### Endpoint

```http
PUT /auth/usuarios/{id}
```

### cURL

```bash
curl -X PUT http://localhost:8080/auth/usuarios/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{
    "nome": "Lucas Hideki",
    "email": "novo@email.com",
    "cep": "01310100"
  }'
```

### Header de autenticação

O token obtido no login deve ser enviado no header:

```http
Authorization: Bearer SEU_TOKEN
```

Por exemplo:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 4. UsuarioRequestDTO

O endpoint de atualização utiliza o `UsuarioRequestDTO`.

```java
public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos")
        String cep
) {
}
```

### Campos

| Campo   | Obrigatório | Validação                                          |
| ------- | ----------- | -------------------------------------------------- |
| `nome`  | Sim         | Entre 1 e 100 caracteres                           |
| `email` | Sim         | Formato de email válido e máximo de 150 caracteres |
| `cep`   | Sim         | Exatamente 8 dígitos                               |

### Body

```json
{
  "nome": "Lucas Hideki",
  "email": "novo@email.com",
  "cep": "01310100"
}
```

> A senha **não faz parte do `UsuarioRequestDTO`** e, portanto, não deve ser enviada na atualização de usuário.

---

## 5. Exemplo utilizando Postman

### Cadastro

**Método:**

```http
POST
```

**URL:**

```text
http://localhost:8080/auth/register
```

**Headers:**

```http
Content-Type: application/json
```

**Body → raw → JSON:**

```json
{
  "nome": "Lucas Hideki",
  "email": "lucas@email.com",
  "senha": "123456"
}
```

---

### Login

**Método:**

```http
POST
```

**URL:**

```text
http://localhost:8080/auth/login
```

**Headers:**

```http
Content-Type: application/json
```

**Body → raw → JSON:**

```json
{
  "email": "lucas@email.com",
  "senha": "123456"
}
```

A resposta será:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Atualização de usuário

**Método:**

```http
PUT
```

**URL:**

```text
http://localhost:8080/auth/usuarios/1
```

**Headers:**

```http
Content-Type: application/json
Authorization: Bearer SEU_TOKEN
```

**Body → raw → JSON:**

```json
{
  "nome": "Lucas Hideki",
  "email": "novo@email.com",
  "cep": "01310100"
}
```

---

## 6. Permissões

O endpoint de atualização possui a seguinte configuração:

```java
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
```

Isso significa que o usuário precisa estar autenticado e possuir uma das seguintes roles:

* `ADMIN`
* `USER`

Usuários sem autenticação ou sem uma dessas roles não poderão acessar o endpoint.

---

## 7. Endpoints públicos e protegidos

| Método | Endpoint              | Autenticação | Role              |
| ------ | --------------------- | ------------ | ----------------- |
| `POST` | `/auth/register`      | Não          | Pública           |
| `POST` | `/auth/login`         | Não          | Pública           |
| `PUT`  | `/auth/usuarios/{id}` | Sim          | `USER` ou `ADMIN` |

---

## 8. Fluxo de autenticação

```text
┌──────────────┐
│    Cliente   │
└──────┬───────┘
       │
       │ POST /auth/register
       │ Dados do usuário
       ▼
┌──────────────┐
│  Cadastro    │
└──────┬───────┘
       │
       │ POST /auth/login
       │ Email + senha
       ▼
┌────────────────────┐
│ Spring Security    │
│ Autenticação       │
└─────────┬──────────┘
          │
          │ JWT
          ▼
┌────────────────────┐
│    Token JWT       │
└─────────┬──────────┘
          │
          │ Authorization: Bearer <token>
          ▼
┌────────────────────────────┐
│ PUT /auth/usuarios/{id}    │
└────────────┬───────────────┘
             │
             │ @PreAuthorize
             ▼
      ┌───────────────┐
      │ USER / ADMIN  │
      └───────────────┘
```

---

## 9. Resumo

O processo para acessar um endpoint protegido é:

```text
1. Cadastro
   POST /auth/register

2. Login
   POST /auth/login

3. Recebe o JWT
   { "token": "..." }

4. Envia o JWT na requisição
   Authorization: Bearer <token>

5. Spring Security valida o token
   ↓

6. Verifica a role do usuário
   ↓

7. Permite ou bloqueia o acesso
```

## 📌 Status do projeto

O Campus Gigs encontra-se em desenvolvimento, com a estrutura principal da API, autenticação, gerenciamento de usuários, serviços e contratações implementada.

A arquitetura foi preparada para permitir a evolução da plataforma com novos recursos e regras de negócio.

---
