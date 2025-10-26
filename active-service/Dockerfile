FROM openjdk:21-jdk-slim
WORKDIR /app
COPY  active-service/target/active-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3002
ENTRYPOINT ["java", "-jar", "app.jar"]
