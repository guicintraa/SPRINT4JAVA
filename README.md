# Sprint 04 – API de Lembretes (Quarkus + Oracle)

## Integrantes
-Guilherme Batista Cintra RM 562850
Davi Tagawa Schincaglia Lima Lemos RM 563457
Caio Felipe Silva RM 564615

## Requisitos atendidos
- API REST com Quarkus 3 (Java 17)
- Conexão Oracle via JDBC (Agroal DataSource)
- CRUD de Lembretes
- Validações simples e tratamento de erro
- Scripts SQL (criação, carga e relatórios)
- Coleção do Insomnia para testes
- CORS habilitado para uso pelo Front-end

## Stack
- Java 17, Quarkus 3.x (Resteasy + Jackson + Agroal)
- Oracle (FIAP) – `jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl`

## Como rodar localmente
### 1) Pré-requisitos
- JDK 17
- **Sem** necessidade de Maven instalado (usa o wrapper `mvnw`)

### 2) Configurar `src/main/resources/application.properties`

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
