# API Lembretes – Quarkus + Oracle (Sprint Java)

API REST para gerenciar **t_lembretes**, vinculados a **t_consulta** (Oracle).

## Stack
- Java 17, Maven
- Quarkus 3 (RESTEasy + JDBC Oracle)
- Padrão DAO (sem JPA/Hibernate)
- Insomnia para testes
- CORS habilitado para o front

## Configuração
Edite `src/main/resources/application.properties`:
```properties
quarkus.datasource.db-kind=oracle
quarkus.datasource.username=rm562850
quarkus.datasource.password=260806
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
quarkus.http.port=8080
quarkus.http.cors=true
