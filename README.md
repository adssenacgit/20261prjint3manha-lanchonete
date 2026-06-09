# Backend PRJINT3 - Java 21 + Spring Boot + MySQL + Swagger

Projeto backend REST criado com:

- Java 21
- Spring Boot 3.5.11
- Spring Web
- Spring Data JPA
- Bean Validation
- MySQL Connector/J
- SpringDoc OpenAPI / Swagger UI

## Regra de status

O projeto aplica exclusão lógica:

- `-1` = registro apagado logicamente
- `0` = registro inativo
- `1` = registro ativo

Por padrão:

- `GET /api/...` retorna registros não apagados, ou seja, status diferente de `-1`.
- `GET /api/.../ativos` retorna apenas registros com status `1`.
- `DELETE /api/.../{id}` não remove fisicamente, apenas marca o status como `-1`.
- `PATCH /api/.../{id}/status/{status}` altera o status para `-1`, `0` ou `1`.

## Atenção sobre a estrutura original do banco

As tabelas `categoria` e `itens_pedidos` não tinham coluna de status no SQL enviado.
Como a regra solicitada exige exclusão lógica em todos os cadastros, o projeto considera:

- `categoria.categoria_status`
- `itens_pedidos.item_pedido_status`

Execute o arquivo:

```sql
database/alter-status-logico.sql
```

Ou crie o banco já com o arquivo:

```sql
database/schema-ajustado.sql
```

## Como configurar o banco

Edite `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/20261_prjint3_manha_felipeespinola?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=latin1
spring.datasource.username=root
spring.datasource.password=senha
```

## Como rodar

```bash
mvn clean spring-boot:run
```

## Swagger

Depois de iniciar o projeto, acesse:

```text
http://localhost:8080/swagger-ui.html
```

## Endpoints principais

- `/api/categorias`
- `/api/produtos`
- `/api/usuarios`
- `/api/pedidos`
- `/api/itens-pedidos`

Cada recurso possui:

- `GET /api/recurso`
- `GET /api/recurso/ativos`
- `GET /api/recurso/{id}`
- `POST /api/recurso`
- `PUT /api/recurso/{id}`
- `PATCH /api/recurso/{id}/status/{status}`
- `DELETE /api/recurso/{id}`

## Exemplos rápidos de JSON

### Categoria

```json
{
  "nome": "Bebidas",
  "status": 1
}
```

### Produto

```json
{
  "nome": "Café Especial",
  "descricao": "Café torrado e moído",
  "preco": 25.90,
  "imagemUrl": "https://exemplo.com/cafe.jpg",
  "status": 1,
  "categoriaId": 1
}
```

### Usuário

```json
{
  "nome": "Administrador",
  "login": "admin",
  "senha": "123456",
  "status": 1
}
```

### Pedido

```json
{
  "numero": 1001,
  "data": "2026-06-03T10:30:00",
  "total": 51.80,
  "status": 1
}
```

### Item do pedido

```json
{
  "quantidade": 2,
  "precoUnitario": 25.90,
  "subtotal": 51.80,
  "status": 1,
  "pedidoId": 1,
  "produtoId": 1
}
```

## Observação de segurança

O campo `usuario_senha` foi mantido conforme a estrutura enviada. Para ambiente real, o ideal é armazenar senha com hash, por exemplo BCrypt, e não retornar senha em respostas da API.
