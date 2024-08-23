FROM maven:3.8.5-openjdk-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/WebLibrary-1.0.0.jar .

CMD ["java", "-jar", "WebLibrary-1.0.0.jar"]

EXPOSE 8080
