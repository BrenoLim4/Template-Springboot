#mvn -N io.takari:maven:wrapper
# Etapa 1: Construir o binário nativo usando GraalVM
FROM ghcr.io/graalvm/native-image-community:21 AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml ./

RUN ./mvnw dependency:go-offline -Pnative -q 2>/dev/null
#COPY src/main/resources/META-INF ./META-INF
COPY src ./src
#RUN ./mvnw clean package -DskipTests -q
#RUN ./mvnw -Pnative -Dagent exec:exec@java-agent
#RUN ./mvnw -Pnative -DskipTests -q -Dagent package
#RUN java -agentlib:native-image-agent=config-output-dir=./config -jar ./target/convenios-api.jar
# Construir a aplicação utilizando o Maven
#RUN ./mvnw -Pnative -q -Dnative-image.arguments=-H:ReflectionConfigurationFiles=./src/main/resources/META-INF/native-image/reflect-config.json -DskipTests native:compile 2>/dev/null && chmod +x ./target/convenios-api
RUN ./mvnw -Pnative -DskipTests -q native:compile 2>/dev/null && chmod +x ./target/convenios-api
#RUN #./mvnw -Pnative exec:exec@native 2>/dev/null && chmod +x ./target/convenios-api

# Etapa 2: Criar a imagem final
FROM ubuntu:22.04 AS deploy
ENV TZ="America/Fortaleza"
# Definir o diretório de trabalho
WORKDIR /app
# Copiar o binário nativo da etapa de construção
COPY --from=build /app/target/convenios-api ./convenios
# Expor a porta (se necessário)
EXPOSE 8093

# Comando de inicialização
CMD ["./convenios"]
