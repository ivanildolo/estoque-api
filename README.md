
# Estoque API

### Sistema para Gerenciamento de Estoque

Este projeto fornece uma API para gerenciamento de estoque, permitindo operações básicas de login e manipulação de produtos.

# Estoque API

### Sistema para Gerenciamento de Estoque

Este projeto fornece uma API para gerenciamento de estoque, permitindo operações básicas de login e manipulação de produtos.

## Ferramentas Necessárias

Para rodar este projeto, você precisará das seguintes ferramentas:

- **Java JDK 21** ou superior: O ambiente de desenvolvimento Java.
- **Maven**: Para gerenciamento de dependências e construção do projeto.
- **IDE IntelliJ** (ou outra de sua preferência): Recomendada para desenvolvimento em Java.
- **Docker**: Para executar o banco de dados Postgres.
- **Git**: Para controle de versão do código.

## Instruções para Execução

### Rodar o Docker: Banco de Dados Postgres

Para iniciar o banco de dados, utilize o comando:

    docker-compose up
### Acessar o Banco de Dados com pgAdmin

Após iniciar o Docker, você pode acessar o pgAdmin usando as seguintes credenciais:

-   **Email**: ivanildosilvalima@gmail.com
-   **Senha**: admin123

1.  Abra o pgAdmin em seu navegador (geralmente em `http://localhost:80`).
2.  Faça login com as credenciais fornecidas.
3.  Adicione um novo servidor no pgAdmin, configurando-o com os seguintes dados:
    -   **Nome do Servidor**: (Escolha um nome)
    -   **Host**: `postgres` (dependendo da configuração do Docker)
    -   **Porta**: `5432`
    -   **Nome do Banco de Dados**: `estoque`
    -   **Usuário**: `postgres`
    -   **Senha**: `admin123` (ou a senha configurada no seu `docker-compose.yml`)

### 3. Iniciar o Projeto

Para rodar o projeto Spring Boot, você pode usar uma das seguintes ferramentas:

#### a. Usando a IDE IntelliJ

1.  Abra o projeto na IntelliJ.
2.  Certifique-se de que todas as dependências estão corretamente configuradas.
3.  Clique com o botão direito na classe principal (geralmente marcada com `@SistemaDeGerenciamentoDeEstoque`).
4.  Edite configuracao do debug acrescente `--spring.profiles.active=dev` no campo Program arguments
4.  Selecione "Run" para iniciar a aplicação.

#### url api local: http://localhost:8080/api/v1/products
#### Documentação da api http://localhost:8080/swagger-ui.html
