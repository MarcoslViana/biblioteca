# Sistema de Cadastro de Livros - Biblioteca Municipal

Sistema CRUD desenvolvido em Spring Boot para gerenciar o acervo de livros da Biblioteca Municipal.

## 📋 Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

## 🚀 Instalação e Execução

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd biblioteca
```

### 2. Compile o projeto
```bash
mvn clean install
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`


## 📚 Endpoints da API

### Base URL: `http://localhost:8080/api/livros`

#### 1. Cadastrar Livro
```http
POST /api/livros
Content-Type: application/json

{
  "isbn": 1234567890,
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "anoDePublicacao": 1899,
  "quantidadeEstoque": 10
}
```

#### 2. Listar Todos os Livros
```http
GET /api/livros
```

#### 3. Buscar Livro por ID
```http
GET /api/livros/{id}
```

#### 4. Buscar Livro por ISBN
```http
GET /api/livros/isbn/{isbn}
```

#### 5. Atualizar Livro
```http
PUT /api/livros/{id}
Content-Type: application/json

{
  "isbn": 1234567890,
  "titulo": "Dom Casmurro - Edição Especial",
  "autor": "Machado de Assis",
  "anoDePublicacao": 1899,
  "quantidadeEstoque": 15
}
```

#### 6. Remover Livro
```http
DELETE /api/livros/{id}
```

## 🧪 Testes

Execute os testes unitários com:
```bash
mvn test
```

Os testes cobrem todas as operações CRUD:
- ✅ Criar livro (sucesso e validação de ISBN duplicado)
- ✅ Buscar por ID (sucesso e livro não encontrado)
- ✅ Buscar por ISBN (sucesso e livro não encontrado)
- ✅ Listar todos os livros
- ✅ Atualizar livro (sucesso, livro não encontrado, ISBN duplicado)
- ✅ Remover livro (sucesso e livro não encontrado)

## 🗄️ Banco de Dados

O projeto utiliza **H2 Database** (banco em memória).

### Acessar Console H2
1. Execute a aplicação
2. Acesse: `http://localhost:8080/h2-console`
3. JDBC URL: `jdbc:h2:mem:biblioteca`
4. Username: `sa`
5. Password: (deixe em branco)

## 🛠️ Stack Tecnológica

- **Java 17+**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **JUnit 5** e **Mockito** (testes)
- **Maven** (gerenciamento de dependências)
- **Lombok** (redução de boilerplate)
- **Spring Validation** (validação de dados)

