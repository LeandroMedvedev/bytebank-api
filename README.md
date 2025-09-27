# 🏦 ByteBank API

API RESTful para um minissistema bancário, desenvolvida como parte do projeto de Bloco de Engenharia de Softwares Escaláveis do Instituto Infnet.

---

## 📖 Sobre o Projeto

O ByteBank é uma API _backend_ que simula as operações essenciais de um banco digital. Ela permite a gestão de usuários (clientes), a criação e administração de contas bancárias e a execução de transações financeiras como depósitos, saques e transferências.

A arquitetura foi construída de forma modular e em camadas (Controller, Service, Repository), utilizando DTOs para garantir um contrato de API seguro e desacoplado da lógica de persistência.

---

## 🛠️ Tecnologias Utilizadas

* ☕ **Java 21**
* 🌱 **Spring Boot 3**
* 💾 **Spring Data JPA & Hibernate**: Para persistência de dados.
* 🔐 **Spring Security**: Para _hashing_ de senhas com BCrypt.
* ✔️ **Jakarta Bean Validation**: Para validação dos dados de entrada.
* 🧪 **H2 Database**: Banco de dados em memória para ambiente de desenvolvimento.
* 🏛️ **Maven**: Gerenciador de dependências e _build_.
* 📄 **Lombok**: Para reduzir código repetitivo (_boilerplate_).

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

* Java (JDK) 21 ou superior.
* Apache Maven 3.8 ou superior.

### Passos

1.  Clone o repositório:
    ```bash
    git clone git@github.com:LeandroMedvedev/bytebank-api.git
    
    cd bytebank-api
    ```
2.  Execute a aplicação usando o _plugin_ do Maven:
    ```bash
    mvn spring-boot:run
    ```
3.  A API estará disponível em `http://localhost:8080`.

---

## 🗺️ Diagrama Entidade Relacionamento (DER)

O modelo relacional da aplicação foi projetado para normalizar os dados e garantir a integridade referencial entre as entidades.

```
/docs
└── ERD.png
```

![DER](/docs/ERD.png)

---

## ✨ Funcionalidades

A API atualmente suporta as seguintes funcionalidades:

* **Gestão de Usuários**: CRUD completo, com validação de dados únicos e senha segura.
* **Gestão de Contas**: CRUD completo, com contas sempre associadas a um usuário.
* **Operações Financeiras**: Depósitos, saques e transferências com validações de regras de negócio (saldo, status da conta, etc.).
* **Tratamento de Erros**: Respostas de erro padronizadas e centralizadas para uma melhor experiência do consumidor da API.

---

## 📚 Endpoints da API

A seguir, a documentação dos endpoints disponíveis.

### 👤 Usuários (`/users`)

| Verbo | Rota | Descrição                                 | Corpo da Requisição (Exemplo) | Resposta de Sucesso |
| :--- | :--- |:------------------------------------------|:---|:---|
| `POST` | `/users` | Cria um novo usuário.                     | `{ "name": "Ana", "email": "ana@email.com", "password": "...", "documentNumber": "..." }` | `201 Created` com os detalhes do usuário. |
| `GET` | `/users` | Lista todos os usuários.                  | N/A | `200 OK` com a lista de usuários. |
| `GET` | `/users/{id}` | Busca um usuário por ID.                  | N/A | `200 OK` com os detalhes do usuário. |
| `GET` | `/users?email={email}` | Busca um usuário por e-mail.              | N/A | `200 OK` com os detalhes do usuário. |
| `PUT` | `/users/{id}` | Atualiza o nome e/ou email de um usuário. | `{ "name": "Ana Silva", "email": "ana.silva@email.com" }` | `200 OK` com os dados atualizados. |
| `DELETE`| `/users/{id}` | Exclui um usuário (se não tiver contas).  | N/A | `204 No Content` |
| `GET` | `/users/{userId}/accounts` | Lista todas as contas de um usuário.      | N/A | `200 OK` com a lista de contas. |

### 🏦 Contas (`/accounts`)

| Verbo | Rota | Descrição | Corpo da Requisição (Exemplo) | Resposta de Sucesso |
| :--- | :--- |:---|:---|:---|
| `POST` | `/accounts` | Cria uma nova conta para um usuário. | `{ "userId": 1, "accountType": "CHECKING" }` | `201 Created` com os detalhes da conta. |
| `GET` | `/accounts/{id}` | Busca uma conta por ID. | N/A | `200 OK` com os detalhes da conta. |
| `PUT` | `/accounts/{id}` | Atualiza o status da conta (ativa/inativa). | `{ "isActive": false }` | `200 OK` com os dados atualizados. |
| `DELETE`| `/accounts/{id}` | Exclui uma conta. | N/A | `204 No Content` |

### 💸 Transações (`/transactions`)

| Verbo | Rota | Descrição | Corpo da Requisição (Exemplo) | Resposta de Sucesso |
| :--- | :--- |:---|:---|:---|
| `POST` | `/transactions/deposit` | Realiza um depósito em uma conta. | `{ "destinationAccountNumber": "123456", "amount": 150.50 }` | `201 Created` com o "recibo" da transação. |
| `POST` | `/transactions/withdrawal` | Realiza um saque de uma conta. | `{ "sourceAccountNumber": "123456", "amount": 50.00 }` | `201 Created` com o "recibo" da transação. |
| `POST` | `/transactions/transfer` | Realiza uma transferência entre contas. | `{ "sourceAccountNumber": "123456", "destinationAccountNumber": "789012", "amount": 25.00 }` | `201 Created` com o "recibo" da transação. |

---

## 📈 Próximos Passos

* [ ] Implementar **paginação** no extrato bancário.
* [ ] Adicionar **filtros** por `query params` na listagem de transações (ex: por período).
* [ ] Implementar **autenticação e autorização** completa (ex: com JWT).
* [ ] Adicionar uma camada de segurança extra com `Headers` (ex: `X-API-KEY`).
