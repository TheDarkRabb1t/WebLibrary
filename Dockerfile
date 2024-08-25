FROM openjdk:21-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/
COPY src/main/resources ./src/main/resources

RUN mvn clean package -DskipTests

RUN mv target/*.jar /app/WebLibrary.jar

EXPOSE 8080

CMD ["java", "-jar", "WebLibrary.jar"]
