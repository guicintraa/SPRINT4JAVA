# ===== build =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /project

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
# força fast-jar e usa o goal do plugin do Quarkus
RUN mvn -q -DskipTests -Dquarkus.package.jar.type=fast-jar clean package \
 && echo "=== TARGET ===" && ls -la target && ls -la target/quarkus-app

# ===== runtime =====
FROM eclipse-temurin:21-jre
WORKDIR /opt/quarkus-app

# copia a pasta gerada pelo fast-jar
COPY --from=build /project/target/quarkus-app/ ./

EXPOSE 8080
# Render injeta PORT; Quarkus lê via system property
CMD ["sh","-c","java -Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT} -jar /opt/quarkus-app/quarkus-run.jar"]
