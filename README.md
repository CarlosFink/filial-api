
# API Filial

Esta API prove todas as operações para manutebilidade do cadastro de filiais

Este projeto foi desenvolvido com uma implementação da composição de dois containers Docker como segue:

- PostgreeSQL database 
- Java Backend (Spring Boot)

O entry point para o usuário é a URL **http://localhost:8080/**

### Pré-requisitos 

Para executar esta api, você precisa instalar duas ferramentas: **Docker** e **Docker Compose**.
Instruções para instalação do **Docker**:

- [Ubuntu] (https://docs.docker.com/install/linux/docker-ce/ubuntu/)
- [Windows] (https://docs.docker.com/docker-for-windows/install/)
- [Mac] (https://docs.docker.com/docker-for-mac/install/)

**Docker Compose** já está incluido nos pacotes de instalação para *Windows* e *Mac*, apenas usuários do *Ubuntu* precisam instalar usando (https://docs.docker.com/compose/install/)

### Como executar?

Abra um terminal na raiz do projeto e execute o comando  
``` $ docker compose up -d ``` para encerrar execute o comando ``` $ docker compose down ```

Este é um aplicativo baseado em Spring Boot (Java) que conecta a um banco de dados e expõe os endpoints REST que podem ser consumidos por qualquer aplicação.
A lista completa de endpoints e seus detalhes pode ser encontrada em **http://localhost:8080/swagger-ui/index.html**