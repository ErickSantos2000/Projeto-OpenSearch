# Usando Amazon Corretto 17
FROM amazoncorretto:17

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo JAR da sua aplicação
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

# Exponha a porta da aplicação
EXPOSE 8080
