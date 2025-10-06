# Sistema de Gerenciamento de Filmes

Um sistema desktop desenvolvido em Java para gerenciar uma coleção de filmes, séries, shows e documentários.

## Requisitos

- Java 21
- PostgreSQL
- Maven

## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `filmes`
2. Configure as credenciais de acesso no arquivo `config.properties` em `src/main/resources`

## Como Gerar Arquivos .jar e .exe

1. No intellij abra o Maven do lado direito da tela
2. Na pasta Lifecycle dê um clique duplo em clean (vai limpar/apagar a pasta target)
3. Dê um clique duplo em Lifecycle - compile
4. Dê um clique duplo em Lifecycle - package
5. Veirifque na pasta target os arquivos criados (copie e cole onde deseja o arquivo .exe)

## Como Executar

```bash
mvn clean package
java -jar target/movie-manager-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Como Gerar BackUp do Banco de Dados Postgres

1. No terminal ou Power Shell: PS C:\FABRICIO> pg_dump -U postgres -h localhost -p 5432 -d filmes -f C:\FABRICIO\filmes_backup.sql
2. Coloque a senha: root
3. Sera gerado o arquivo filmes_backup.sql na pasta FABRICIO
4. No destino (onde se deseja colocar o BackUp criado): no pgadmin crie o bd filmes (Vazio) ou outro que queira
5. PS C:\FABRICIO> psql -U postgres -h localhost -p 5432 -d filmes -f C:\FABRICIO\filmes_backup.sql
6. coloca a senha: root
7. De um refresh. Pronto, verifique as tabelas do BD (todos os dados devem estar copiados)

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