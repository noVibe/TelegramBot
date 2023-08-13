FROM maven:3.8.6-openjdk-18-slim
COPY src /src
COPY pom.xml .
RUN mvn -f /pom.xml clean package
CMD ["java","-jar","target/TelegramBot-0.0.1-SNAPSHOT.jar"]