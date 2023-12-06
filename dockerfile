FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY docker-compose.yml .
COPY src src
RUN mvn clean install -DskipTests -Dlicense.skip -Ddockerfile.skip=true