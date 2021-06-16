FROM openjdk:11.0.11-jdk-slim
WORKDIR /workdir
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]