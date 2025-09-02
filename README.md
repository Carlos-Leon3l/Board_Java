# Board_java

## Descrição

Board_java é um sistema de quadros de tarefas (kanban) baseado em console, desenvolvido em Java. Ele permite que os usuários criem, gerenciem e visualizem quadros, colunas e cartões, simulando um fluxo de trabalho. O projeto utiliza um banco de dados MySQL para persistir os dados e o Liquibase para gerenciar as migrações do esquema do banco de dados.

## Funcionalidades

* **Gerenciamento de Quadros (Boards):**
    * Criar novos quadros com colunas personalizadas.
    * Selecionar um quadro existente para visualizar e interagir.
    * Excluir quadros.

* **Gerenciamento de Cartões (Cards):**
    * Criar novos cartões com título e descrição.
    * Mover cartões entre as colunas do quadro.
    * Bloquear e desbloquear cartões com um motivo.
    * Cancelar cartões, movendo-os para uma coluna de cancelamento.
    * Visualizar detalhes de um cartão específico.

* **Visualização:**
    * Exibir a estrutura completa de um quadro, incluindo suas colunas e a quantidade de cartões em cada uma.
    * Visualizar o conteúdo de uma coluna específica, listando todos os seus cartões.

## Tecnologias Utilizadas

* **Java 21:** Versão da linguagem de programação utilizada no projeto.
* **Gradle:** Ferramenta de automação de compilação.
* **MySQL:** Sistema de gerenciamento de banco de dados relacional.
* **Liquibase:** Ferramenta para controle de versão e migração de esquemas de banco de dados.
* **Lombok:** Biblioteca para reduzir código boilerplate (getters, setters, construtores).
* **JDBC:** API Java para conectividade com bancos de dados.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes principais:

* `br.com.UI`: Contém as classes responsáveis pela interface do usuário no console (`MainMenu` e `BoardMenu`).
* `br.com.dto`: Contém os Data Transfer Objects (DTOs) para transferir dados entre as camadas da aplicação.
* `br.com.exception`: Define as exceções customizadas da aplicação.
* `br.com.persistance`:
    * `Config`: Classe para configuração da conexão com o banco de dados.
    * `converter`: Classes para conversão de tipos (ex: `OffsetDateTime`).
    * `dao`: Data Access Objects, responsáveis pela comunicação com o banco de dados.
    * `entity`: Classes que representam as entidades do banco de dados.
    * `migration`: Classe para execução das migrações do Liquibase.
* `br.com.service`: Classes que contêm a lógica de negócio da aplicação.

## Como Executar o Projeto

### Pré-requisitos

* Java 21 ou superior instalado.
* Gradle instalado.
* MySQL Server em execução.

### Configuração do Banco de Dados

1.  Crie um banco de dados no MySQL e coloque nas configurações de conexão.
2.  Configure as credenciais de acesso ao banco de dados nos seguintes arquivos:
    * `src/main/java/com/example/board_java/br/com/persistance/Config/ConnectionConfig.java`
    * `src/main/resources/liquibase.properties`

    Altere as variáveis `url`, `user`/`username` e `password` de acordo com a sua configuração local do MySQL.

### Execução

1.  Clone o repositório para a sua máquina local.
2.  Abra um terminal na pasta raiz do projeto.
3.  Execute a aplicação utilizando o Gradle:

    ```bash
    ./gradlew run
    ```

    Isso irá compilar o projeto e executar a classe `Main`, que por sua vez aplicará as migrações do Liquibase (se necessário) e iniciará a interface de console.

4.  Siga as instruções apresentadas no console para interagir com o sistema.
