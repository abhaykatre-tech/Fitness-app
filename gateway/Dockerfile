FROM openjdk:21-jdk-slim
WORKDIR /app
COPY gateway/target/gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "app.jar"]
