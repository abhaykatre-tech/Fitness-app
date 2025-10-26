FROM openjdk:21-jdk-slim
WORKDIR /app
COPY user-service/target/user-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "app.jar"]
