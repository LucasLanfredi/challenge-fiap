# Stage 1: Build da aplicação com Maven e Java 21 usando imagem oficial
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copia o arquivo de configuração do Maven
COPY pom.xml .
# Faz o download das dependências para cache
RUN mvn dependency:go-offline -B
# Copia o restante do código-fonte
COPY src ./src
# Realiza o build da aplicação (pulando os testes, se desejar)
RUN mvn clean package -DskipTests

# Stage 2: Cria a imagem final utilizando Java 21 para execução
FROM openjdk:21-slim-buster
WORKDIR /app
# Copia o jar gerado no stage de build
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta 8080
EXPOSE 8080
# Define o comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]