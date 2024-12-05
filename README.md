# Lista de Tarefas
link: http://15.228.18.195:8080/swagger-ui/swagger-ui/index.html#/
\ou\
http://15.228.18.195:8080/swagger-ui/index.html#/
\⚠️ o endpoint de upload foi feito para ser usado no postman ou algum hhtp client para uso da opção de arquivo: http://15.228.18.195:8080/swagger-ui/swagger-ui/index.html#/tarefa-controller/uploadFile

## Objetivos:
Desenvolver uma aplicação completa utilizando a stack tecnológica estudada ao longo do semestre, integrando conhecimentos de AWS SQS, AWS S3, Docker, Docker-Compose, Java, Spring-Boot, comunicação via API REST JSON, AWS EC2, Git, GitHub, GitHub Actions e AWS DynamoDB.

## Descrição do Trabalho:

Vocês deverão desenvolver uma aplicação web para gerenciamento de tarefas (To-Do List) que permitirá aos usuários criar, atualizar, visualizar e deletar tarefas. A aplicação deve contemplar as seguintes funcionalidades e tecnologias:

Backend (Tecnologia a critério do aluno):

Implementar um serviço RESTful para gerenciar as tarefas (CRUD).\
✅Incluir tarefa;\
✅Apagar tarefa;\
✅Consultar lista de tarefas;\
✅Atualizar tarefa;\
✅Persistir os dados das tarefas em um banco de dados (pode ser o AWS DynamoDB ou algum escolhido por vocês)\
✅Utilizar AWS SQS ou outra mensageria para envio de notificações assíncronas quando uma tarefa for criada ou atualizada. A notificação vai conter o seguinte payload
```json
{
    "descricao": <DESCRICAO DA TAREFA>, 
    "id": <ID DA TAREFA> 
}
```
## Armazenamento de arquivos: 
✅O APP deve permitir upload de arquivos relacionados às tarefas, armazenando-os no AWS S3. 

## Containerização:

✅Containerizar a aplicação backend utilizando Docker.\
✅Utilizar Docker-Compose para orquestrar o backend e outros serviços necessários.
## Deploy na Nuvem:

✅Realizar o deploy da aplicação em uma instância EC2 da AWS.\
✅Configurar o ambiente de produção usando Docker-Compose.
## Controle de Versão e Integração Contínua:

✅Utilizar Git para controle de versão do código.\
✅Hospedar o repositório no GitHub.\
Configurar GitHub Actions para realizar\
✅a integração contínua,\
✅executando testes automatizados\
✅e realizando o deploy automático para a AWS EC2.
# Requisitos Específicos:

API RESTful:

✅Endpoints para criar, ler, atualizar e deletar tarefas.\
✅Endpoint para upload de arquivos relacionados às tarefas.\
✅Documentação da API utilizando [Swagger](http://localhost:8080/swagger-ui/swagger-ui/index.html#/) ou similar.\
Mensageria:

✅Configuração de uma fila SQS ou outra mensageria para envio de notificações assíncronas. Eu recomendo SQS que é mais fácil.\
✅Envio de mensagens para a fila sempre que uma tarefa for criada ou atualizada.\
AWS S3:

✅Configuração de um bucket S3 para armazenamento de arquivos.\
✅Endpoint para upload e download de arquivos relacionados às tarefas.\
Banco de dados:

✅Configuração de um banco de dados. Eu recomendo tabela DynamoDB para armazenamento das tarefas.\
Docker e Docker-Compose:

✅Dockerfile para a aplicação backend.\
✅docker-compose.yml para orquestração dos serviços necessários.\
Deploy:

✅Deploy da aplicação em uma instância EC2 da AWS.\
Git e GitHub Actions:

✅Repositório Git hospedado no GitHub.\
✅Pipeline de integração contínua configurado com GitHub Actions.

<details>
<summary>Referências:</summary>

[AWS Tutorial 1 - Launching EC2 Ubuntu Machine on AWS](https://www.youtube.com/watch?v=osqZnijkhtE&ab_channel=KGPTalkie)\
[Spring Boot With Amazon S3 : File Upload & Download Example | S3 Bucket | JavaTechie](https://www.youtube.com/watch?v=vY7c7k8xmKE&ab_channel=JavaTechie)\
[AWS SQS: Como publicar e consumir mensagens com Spring Cloud AWS](https://www.youtube.com/watch?v=56_F59cIT8M&t=758s&ab_channel=Build%26Run)
[Spring Boot - Deploy na AWS EC2 com Github Actions e Docker](https://www.youtube.com/watch?v=mIuFF_ZP_60&ab_channel=DanieleLe%C3%A3o)\
[Usuários, Roles e Permissões de Acesso AWS IAM - Curso Prático Amazon Web Services - Aula 04 ](https://www.youtube.com/watch?v=spUlvo0HNFQ&ab_channel=GaragemdoInventor)
</details>
