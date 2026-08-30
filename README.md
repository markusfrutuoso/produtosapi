# Produtos API

API REST para gerenciamento de produtos, desenvolvida com **Spring Boot** e **Java 21**. O projeto implementa um CRUD completo (criar, consultar, atualizar e remover) com persistência em banco de dados relacional, além de endpoints de busca com filtros por nome, descrição e preço.

## ✨ Sobre o projeto

Este projeto foi construído como exercício de consolidação de conceitos de desenvolvimento backend com Java e Spring, aplicando boas práticas de organização em camadas (Controller, Repository, Model) e o padrão REST para exposição dos recursos.

## 🛠️ Tecnologias utilizadas

- **Java 21**
- **Spring Boot 4.1.1**
  - Spring Web (MVC)
  - Spring Data JPA
  - Spring Boot DevTools
- **H2 Database** (banco de dados em memória)
- **Lombok**
- **Maven** (gerenciador de dependências e build)
- **JUnit 5** (testes)

## 📁 Estrutura do projeto

```
src/main/java/com/markus/produtosapi
├── ProdutosapiApplication.java     # Classe principal (bootstrap da aplicação)
├── controller/
│   └── ProdutoController.java      # Endpoints REST
├── model/
│   └── Produto.java                # Entidade JPA
└── repository/
    └── ProdutoRepository.java      # Interface de acesso a dados (Spring Data JPA)

src/main/resources
├── application.yml                 # Configurações da aplicação e do banco H2
└── data.sql                        # Script de criação da tabela
```

## 🚀 Como executar o projeto

### Pré-requisitos

- [JDK 21](https://adoptium.net/) ou superior
- Maven (o projeto já inclui o Maven Wrapper, então não é obrigatório ter o Maven instalado)

### Passo a passo

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/produtosapi.git
cd produtosapi

# Execute a aplicação com o Maven Wrapper
./mvnw spring-boot:run
```

A aplicação sobe por padrão na porta `8080`: `http://localhost:8080`

### Console do banco de dados H2

Como o projeto utiliza um banco H2 em memória, é possível inspecionar os dados durante a execução através do console web:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:produtos`
- Usuário: `sa`
- Senha: `password`

## 📌 Endpoints da API

Todos os endpoints têm como base o caminho `/produtos`.

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/produtos` | Cadastra um novo produto |
| `GET` | `/produtos` | Lista todos os produtos |
| `GET` | `/produtos/{id}` | Busca um produto pelo ID |
| `PUT` | `/produtos/{id}` | Atualiza um produto existente |
| `DELETE` | `/produtos/{id}` | Remove um produto pelo ID |
| `GET` | `/produtos/filtro-nome?nome={nome}` | Busca produtos cujo nome contenha o termo informado |
| `GET` | `/produtos/filtro-descricao?descricao={descricao}` | Busca produtos cuja descrição contenha o termo informado |
| `GET` | `/produtos/filtro-preco?preco={preco}` | Busca produtos por preço |

### Exemplos de uso (Postman)

Os exemplos abaixo mostram como testar cada endpoint utilizando o **Postman**.

**Cadastrar um produto**
- Método: `POST`
- URL: `http://localhost:8080/produtos`
- Headers: `Content-Type: application/json`
- Body (raw/JSON):
```json
{
    "nome": "Notebook",
    "descricao": "Notebook 16GB RAM, SSD 512GB",
    "preco": 4500.00
}
```

**Listar todos os produtos**
- Método: `GET`
- URL: `http://localhost:8080/produtos`

**Buscar produto por ID**
- Método: `GET`
- URL: `http://localhost:8080/produtos/{id}`

**Atualizar um produto**
- Método: `PUT`
- URL: `http://localhost:8080/produtos/{id}`
- Headers: `Content-Type: application/json`
- Body (raw/JSON):
```json
{
    "nome": "Notebook",
    "descricao": "Notebook 16GB RAM, SSD 1TB",
    "preco": 4900.00
}
```

**Remover um produto**
- Método: `DELETE`
- URL: `http://localhost:8080/produtos/{id}`

**Filtrar produtos por nome**
- Método: `GET`
- URL: `http://localhost:8080/produtos/filtro-nome?nome=Notebook`

**Filtrar produtos por descrição**
- Método: `GET`
- URL: `http://localhost:8080/produtos/filtro-descricao?descricao=SSD`

**Filtrar produtos por preço**
- Método: `GET`
- URL: `http://localhost:8080/produtos/filtro-preco?preco=4500.00`

### Modelo de dados (`Produto`)

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `String` (UUID) | Identificador único, gerado automaticamente no cadastro |
| `nome` | `String` | Nome do produto |
| `descricao` | `String` | Descrição do produto |
| `preco` | `Double` | Preço do produto |

## ✅ Testes

O projeto conta com a estrutura de testes do Spring Boot (`spring-boot-starter-data-jpa-test` e `spring-boot-starter-webmvc-test`). Para executar os testes:

```bash
./mvnw test
```

Além disso, os testes de integração da API foram realizados manualmente via **Postman**, validando o comportamento de todos os endpoints (cadastro, consulta, atualização, remoção e filtros) contra o banco H2 em execução.

## 🔭 Próximos passos

Algumas melhorias identificadas para evolução do projeto:

- Uso de DTOs para desacoplar a API do modelo de persistência
- Validações de entrada (Bean Validation) nos endpoints de criação e atualização
- Tratamento centralizado de exceções (`@ControllerAdvice`)
- Padronização de respostas HTTP (ex.: `404 Not Found` ao buscar um produto inexistente, em vez de retornar `null`)
- Migração para um banco de dados persistente (ex.: PostgreSQL) em ambiente de produção
- Documentação interativa da API com Swagger/OpenAPI
- Cobertura de testes unitários e de integração automatizados para controller e repository (complementando os testes manuais já feitos no Postman)

## 👤 Autor

Desenvolvido por **Markus**, estudante de Análise e Desenvolvimento de Sistemas (FIAP), como parte dos estudos em desenvolvimento backend com Java.
