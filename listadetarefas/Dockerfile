## Construção
FROM maven:3.8.6-eclipse-temurin-17 AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
