# Stage 1: Build da aplicação com Maven e Java 21 usando imagem oficial
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copia o arquivo de configuração do Maven
COPY pom.xml .
# Faz o download das dependências para cache
RUN mvn dependency:go-offline -B -Pdocker
# Copia o restante do código-fonte
COPY src ./src
# Realiza o build da aplicação
RUN mvn clean package

# Stage 2: Cria a imagem final utilizando Java 21 para execução
FROM eclipse-temurin:21.0.7_6-jre-noble
#FROM openjdk:21-slim-buster
WORKDIR /app
# Copia o jar gerado no stage de build
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta 8080
EXPOSE 8080
# Define o comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=docker"]