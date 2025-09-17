# My Registry API - Sistema de E-commerce

## 📝 Descrição
Esta API é um sistema completo de e-commerce desenvolvido com Spring Boot, oferecendo funcionalidades de autenticação, gerenciamento de usuários, produtos, categorias e pedidos. O sistema implementa controle de acesso baseado em diferentes níveis de usuário e utiliza tokens JWT para autenticação.

## 🔗 Links Úteis
- **Front-end**: [https://api-front-blue.vercel.app/](https://api-front-blue.vercel.app/)
- **Back-end**: [https://api-name-256d95d6775b.herokuapp.com/](https://api-name-256d95d6775b.herokuapp.com/)

## 🛠️ Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token) para autenticação
- BCrypt para criptografia de senhas
- Arquitetura RESTful
- Banco de dados relacional

## 📋 Funcionalidades Principais
- Autenticação e registro de usuários
- Gerenciamento de produtos
- Gerenciamento de categorias
- Carrinho de compras
- Sistema de pedidos com diferentes estados
- Controle de acesso baseado em funções (USER, SELLER, ADMIN, MASTER)

## 🔒 Níveis de Acesso
O sistema possui quatro níveis de usuário:
- **USER (1)**: Acesso básico - pode comprar produtos
- **SELLER (2)**: Pode cadastrar e gerenciar produtos
- **ADMIN (3)**: Gerenciamento avançado
- **MASTER (4)**: Acesso completo ao sistema

## ⚙️ Pré-requisitos
- Java JDK 11 ou superior
- Maven
- Banco de dados compatível com JPA (MySQL, PostgreSQL, H2)

## 🚀 Como executar o projeto

### Clone o repositório
```bash
git clone https://github.com/Victor-GBarbosa/SpringBoot-Api.git
cd SpringBoot-Api
```

### Configure o banco de dados
Edite o arquivo `src/main/resources/application.properties` com as configurações do seu banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### Configure a chave secreta para JWT
Adicione a seguinte propriedade no arquivo `application.properties`:

```properties
jwt.secret=sua_chave_secreta_aqui
```

### Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## 📊 Endpoints da API

### Autenticação
- `POST /auth/login` - Login de usuário
  - Body: `{"login": "email@example.com", "password": "senha"}`
- `POST /auth/register` - Registrar novo usuário

### Usuários
- `GET /users` - Listar todos os usuários (MASTER)
- `GET /users/{id}` - Obter usuário por ID (MASTER)
- `GET /users/email/{email}` - Obter usuário por email (proprietário ou MASTER)
- `PATCH /users/{userEmail}` - Atualizar dados de usuário
- `DELETE /users/{userEmail}` - Deletar usuário (MASTER)
- `GET /users/{userEmail}/orders` - Listar pedidos do usuário
- `GET /users/{userEmail}/cart` - Obter carrinho de compras do usuário
- `PATCH /users/{userEmail}/order/addProduct` - Adicionar produto ao carrinho
- `POST /users/{userEmail}/finishBuy` - Finalizar compra

### Produtos
- `GET /product` - Listar todos os produtos
- `GET /product/{id}` - Obter produto por ID
- `GET /product/email/{email}` - Listar produtos por vendedor
- `POST /product` - Cadastrar novo produto (SELLER+)

### Categorias
- `GET /category` - Listar todas as categorias
- `GET /category/{id}` - Obter categoria por ID
- `POST /category` - Criar nova categoria

### Itens de Pedido
- `GET /orderProduct` - Listar todos os itens de pedido
- `GET /orderProduct/{id}` - Obter item de pedido por ID
- `DELETE /orderProduct/{id}` - Remover item de pedido

## 📝 Exemplos de Requisições

### Login
```json
POST /auth/login
Content-Type: application/json

{
  "login": "exemplo@email.com",
  "password": "senha123"
}
```

### Resposta de Login
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Registro de Usuário
```json
POST /auth/register
Content-Type: application/json

{
  "name": "Nome do Usuário",
  "email": "exemplo@email.com",
  "password": "senha123",
  "phoneNumber": "91 12345-6789",
  "cpf": "123.456.789-00"
}
```

### Criação de Produto
```json
POST /product
Content-Type: application/json
Authorization: seu_token_aqui

{
  "name": "Nome do Produto",
  "price": 99.90,
  "description": "Descrição detalhada do produto",
  "imageUrl": "https://url-da-imagem.com/produto.jpg",
  "category": {
    "id": 1
  },
  "user": {
    "id": 1
  }
}
```

### Adicionar Produto ao Carrinho
```json
PATCH /users/exemplo@email.com/order/addProduct
Content-Type: application/json
Authorization: seu_token_aqui

{
  "product": {
    "id": 1
  },
  "quantity": 2
}
```

## 🔒 Segurança
Para acessar endpoints protegidos, inclua o token JWT no cabeçalho:

```
Authorization: seu_token_aqui
```

## 📦 Estrutura do Projeto
```
src
├── main
│   ├── java
│   │   └── mkn
│   │       └── api
│   │           └── my_registry_api
│   │               ├── config
│   │               │   ├── DevConfig.java
│   │               │   ├── ProdConfig.java
│   │               │   └── security
│   │               │       ├── SecurityConfig.java
│   │               │       ├── SecurityFilter.java
│   │               │       └── authorizationManager
│   │               ├── entities
│   │               │   ├── Category.java
│   │               │   ├── Order.java
│   │               │   ├── OrderProduct.java
│   │               │   ├── Product.java
│   │               │   ├── User.java
│   │               │   ├── dtos
│   │               │   │   ├── AuthenticationDTO.java
│   │               │   │   └── UserPatchRequest.java
│   │               │   └── enums
│   │               │       ├── OrderStatus.java
│   │               │       └── UserRole.java
│   │               ├── repositories
│   │               │   ├── CategoryRepository.java
│   │               │   ├── OrderProductRepository.java
│   │               │   ├── OrderRepository.java
│   │               │   ├── ProductRepository.java
│   │               │   └── UserRepository.java
│   │               ├── resource
│   │               │   ├── AuthenticationResource.java
│   │               │   ├── CategoryResource.java
│   │               │   ├── OrderProductResource.java
│   │               │   ├── ProductResource.java
│   │               │   └── UserResource.java
│   │               └── services
│   │                   ├── AuthorizationService.java
│   │                   ├── CategoryService.java
│   │                   ├── OrderProductService.java
│   │                   ├── OrderService.java
│   │                   ├── ProductService.java
│   │                   ├── TokenService.java
│   │                   └── UserService.java
│   └── resources
│       └── application.properties
└── test
```

## 🔄 Fluxo de Compra
1. Usuário se registra ou faz login
2. Navega pelos produtos disponíveis
3. Adiciona produtos ao carrinho
4. Finaliza a compra
5. O sistema cria um pedido com status PENDING_PAYMENT
6. (Fluxo de pagamento - a ser implementado)
7. Após confirmação, o pedido passa para PAID, depois SHIPPED e finalmente DELIVERED

## 🛡️ Estados de Pedido
- **CART (0)**: Carrinho de compras ativo
- **PENDING_PAYMENT (1)**: Aguardando pagamento
- **PAID (2)**: Pagamento realizado
- **SHIPPED (3)**: Enviado
- **DELIVERED (4)**: Entregue
- **COMPLETED (5)**: Pedido concluído
- **CANCELLED (6)**: Pedido cancelado

## 🤝 Contribuições
Contribuições são bem-vindas! Para contribuir:
1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 👨‍💻 Autor
Victor G. Barbosa

---

Desenvolvido com ❤️
