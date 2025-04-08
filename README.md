# 📝 Blog Pessoal

Projeto desenvolvido como parte do programa **Acelera Maker**, com o objetivo de praticar e fixar os conceitos do ecossistema **Spring Framework**, como Spring Boot, Spring Data JPA, Spring Security e Swagger/OpenAPI. O projeto simula uma plataforma de blog onde usuários autenticados podem criar postagens associadas a temas.

---

## 📚 Descrição

O **Blog Pessoal** é uma aplicação web RESTful que oferece funcionalidades básicas para criação e visualização de postagens em um blog. Ao longo do aprendizado, novas funcionalidades podem ser adicionadas conforme os módulos forem sendo concluídos.

O projeto foi construído com foco em boas práticas de arquitetura, como separação de camadas, uso de DTOs e autenticação via JWT.

---

## 🧩 Recursos

O sistema é composto por três entidades principais e uma classe auxiliar:

| Classe            | Descrição                                                                         |
|------------------|-----------------------------------------------------------------------------------|
| `Usuario`         | Representa um usuário do sistema, que pode criar postagens                        |
| `Postagem`        | Representa uma postagem no blog, contendo título, texto, data, etc.               |
| `Tema`            | Representa a categoria ou tema de uma postagem                                    |
| `UsuarioLoginDTO` | Classe auxiliar usada apenas para o processo de autenticação (login)             |

---

## 🛠️ Tecnologias utilizadas

- ✅ **Java 21**
- ✅ **Spring Boot 3.4.4**
- ✅ **Spring Data JPA**
- ✅ **Spring Security**
- ✅ **JWT (JSON Web Token)** – via `jjwt`
- ✅ **MySQL**
- ✅ **Hibernate**
- ✅ **Bean Validation (Jakarta)**
- ✅ **Swagger/OpenAPI** – via `springdoc-openapi`

---

## 🚀 Como executar

### 🔧 Pré-requisitos

- Java 21
- Maven
- MySQL

### 🗃️ Configuração do banco de dados

Crie um banco no MySQL com o nome `banco` (ou personalize no arquivo `application.properties`).

```sql
CREATE DATABASE banco;
```

### ⚙️ Configuração do `application.properties`

Crie o arquivo `src/main/resources/application.properties` com o seguinte conteúdo:

```properties
spring.application.name=blogpessoal
spring.datasource.url=jdbc:mysql://localhost:porta/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

jwt.secret=seu_jwt_secret

springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.disable-swagger-default-url=true
```

Substitua `seu_user`, `sua_senha` pelas suas credenciais locais do MySQL e `seu_jwt_secret` por sua chave JWT.

---

### ▶️ Executando a aplicação

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/blogpessoal.git
cd blogpessoal
```

2. Execute o projeto:

```bash
./mvnw spring-boot:run
```

3. Acesse a documentação da API:

```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Funcionalidades principais

- ✅ Cadastro e autenticação de usuários
- ✅ Criação, listagem, atualização e exclusão de postagens
- ✅ Associação de postagens com temas
- ✅ Filtro de postagens por tema
- ✅ Segurança com JWT
- ✅ Documentação interativa com Swagger

---

## 📁 Estrutura do projeto

```
src/
├── main/
│   ├── java/br/com/aceleramaker/blogpessoal/
|   |   |── config/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── repository/
│   │   ├── security/
│   │   └── BlogPessoalApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/...
```

---

## 🔐 Segurança

A aplicação utiliza **JWT (JSON Web Token)** para autenticação e autorização. Os tokens são gerados no login e devem ser enviados no cabeçalho `Authorization` das requisições autenticadas:

```http
Authorization: Bearer <token>
```

---

## 🧪 Testes

A aplicação possui cobertura de testes unitários usando **JUnit 5** e **Mockito**, especialmente focados nos controllers (`UsuarioController`, `TemaController`, etc). O uso de `@Mock` ao invés de `@MockBean` é adotado para garantir testes verdadeiramente unitários.

---

## 🔒 Classificação

> Uso exclusivo no contexto do **Programa Acelera Maker**

---

## 👨‍💻 Autor

💻 Desenvolvido por Lucas Maia Dantas – Acelera Maker 🚀
