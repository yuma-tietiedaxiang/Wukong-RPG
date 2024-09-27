FROM maven:3.8-openjdk-17 AS build

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:23-ea-17-jdk-slim

COPY --from=build /target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]