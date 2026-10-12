# Loja

API REST de e-commerce desenvolvida com Spring Boot. O sistema gerencia clientes, funcionários, produtos e vendas, recebe pagamentos via **PIX pelo Mercado Pago** e usa uma **IA (Claude, via Spring AI)** para analisar as vendas do dia e gerar notificações para a gerência, junto com alertas de estoque baixo.

## Funcionalidades

- Cadastro e login de clientes e funcionários, com autenticação JWT
- Perfis de acesso: `VENDEDOR` e `GERENTE`
- Cadastro e listagem de produtos, com controle de estoque
- Registro de transações (vendas) com itens, que baixam o estoque automaticamente
- Pagamento via PIX com QR Code, integrado ao Mercado Pago
- Webhook para confirmar o pagamento e atualizar o total vendido do funcionário
- Rotina diária (Spring Batch) que gera notificações de estoque baixo, vendas realizadas e um resumo analítico produzido por IA
- Ranking de vendas por funcionário

## Arquitetura

<img width="869" height="382" alt="Loja-pix-IA" src="https://github.com/user-attachments/assets/e12231d2-65bc-47cf-b67a-7d2abff41be5" />


- `Pessoa` é uma classe abstrata (herança `JOINED`) da qual herdam `Cliente` e `Funcionario`.
- Uma `Transacao` referencia o cliente, o funcionário que realizou a venda, os itens e o pagamento.
- O `Pagamento` guarda o identificador da cobrança no Mercado Pago (`idTransacaoGateway`), usado para correlacionar o webhook.
- `Notificacao` é independente e armazena os avisos gerados pela rotina diária.

## Fluxo de pagamento

1. O vendedor cria a transação em `POST /api/realizarTransacao`. O estoque dos produtos é reduzido.
2. Em `POST /api/pagamento`, é criado um pagamento com status `PENDENTE` vinculado à transação.
3. Em `POST /api/pagamento/{id}/cobrar`, a API cria a cobrança PIX no Mercado Pago e retorna o QR Code em base64.
4. Quando o cliente paga, o Mercado Pago chama `POST /api/webhooks/mercadopago`.
5. O webhook marca o pagamento como `PAGO` e soma o valor ao total vendido do funcionário.

## Rotina diária com IA

Um job do Spring Batch (`ProdutosJob`) roda todos os dias às 22h e também pode ser disparado manualmente em `POST /api/batch/executar`. Ele executa três etapas:

1. **Estoque:** gera uma notificação para cada produto com estoque igual ou inferior a 5 unidades.
2. **Vendas:** gera uma notificação para cada transação aprovada, identificando o funcionário.
3. **Análise com IA:** consolida as unidades vendidas por produto no dia, envia o resumo ao modelo Claude e salva a mensagem gerada como notificação.

As notificações ficam disponíveis para vendedores e gerentes em `GET /api/notificacao`.

## Tecnologias

- Java 17 e Spring Boot 4
- Spring Web, Data JPA, Security, Validation e Batch
- Spring AI (Anthropic)
- PostgreSQL 16
- SDK Java do Mercado Pago (API de Orders)
- JWT (jjwt)
- Docker e Docker Compose

## Endpoints

| Método | Rota | Acesso |
|--------|------|--------|
| POST | `/api/cadastrarCliente` | Público |
| POST | `/api/loginCliente` | Público |
| POST | `/api/loginFuncionario` | Público |
| POST | `/api/cadastrarFuncionario` | Gerente |
| POST | `/api/cadastrarProduto` | Vendedor, Gerente |
| GET | `/api/produtos` | Público |
| POST | `/api/realizarTransacao` | Vendedor, Gerente |
| POST | `/api/pagamento` | Vendedor, Gerente |
| POST | `/api/pagamento/{id}/cobrar` | Vendedor, Gerente |
| POST | `/api/webhooks/mercadopago` | Público (chamado pelo Mercado Pago) |
| GET | `/api/notificacao` | Vendedor, Gerente |
| GET | `/api/listarFuncionarios` | Gerente |
| GET | `/api/maiorVenda` | Gerente |
| POST | `/api/batch/executar` | Autenticado |

As rotas protegidas exigem o cabeçalho `Authorization: Bearer <token>`, obtido nos endpoints de login.

## Configuração

Crie um arquivo `.env` na raiz do projeto, a partir do `.env.example`:

```env
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=sua_senha
JWT_SECRET=um_segredo_com_pelo_menos_32_caracteres
MERCADOPAGO_ACCESS_TOKEN=seu_access_token
ANTHROPIC_API_KEY=sua_chave
GERENTE_EMAIL=dono@loja.com
GERENTE_SENHA=senha_com_8_ou_mais_caracteres
```

O gerente inicial é criado automaticamente na primeira execução, caso ainda não exista nenhum funcionário.

> Nunca versione o arquivo `.env`. Ele já está no `.gitignore`.

## Executando com Docker

```bash
docker compose up --build
```

O Compose sobe o PostgreSQL primeiro e só inicia a API depois que o banco passa no health check. A API fica disponível em `http://localhost:8081`.

Para consultar o banco:

```bash
docker exec -it loja-db psql -U postgres -d Lojadb -c "SELECT * FROM pagamento;"
```

## Executando localmente

Requisitos: Java 17, Maven e um PostgreSQL em `localhost:5432` com o banco `Lojadb`.

```bash
./mvnw spring-boot:run
```

## Webhook do Mercado Pago

O Mercado Pago precisa de uma URL pública para chamar o webhook. Em ambiente local, exponha a porta 8081 com uma ferramenta como o ngrok e cadastre a URL `https://<seu-dominio>/api/webhooks/mercadopago` no painel de desenvolvedores do Mercado Pago.

## Próximos passos

- Validar a assinatura (`x-signature`) das notificações do webhook
- Tornar o processamento do webhook idempotente
- Atualizar o status da transação e devolver o estoque quando o pagamento for recusado
- Substituir `ddl-auto=update` por migrations (Flyway)
