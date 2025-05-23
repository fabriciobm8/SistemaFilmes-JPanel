# Sistema de Gerenciamento de Filmes

Um sistema desktop desenvolvido em Java para gerenciar uma coleção de filmes, séries, shows e documentários.

## Requisitos

- Java 21
- PostgreSQL
- Maven

## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `filmes`
2. Configure as credenciais de acesso no arquivo `config.properties` em `src/main/resources`

## Como Executar

```bash
mvn clean package
java -jar target/movie-manager-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Funcionalidades

- Cadastro, edição e exclusão de filmes
- Filtragem por múltiplos critérios
- Organização por localização física (estantes, prateleiras)
- Interface gráfica intuitiva com Swing

## Estrutura do Projeto

O projeto segue a arquitetura MVC (Model-View-Controller):

- **Model**: Classes de dados e enumerações
- **View**: Interface gráfica usando Swing
- **Controller**: Lógica de negócios e operações CRUD
- **DAO**: Acesso ao banco de dados PostgreSQL

## Licença

Este projeto é licenciado sob a licença MIT.