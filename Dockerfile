FROM ubuntu:latest AS build
RUN apt-get update && apt-get install -y maven
COPY . .
RUN ./mvnw package

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /path/to/your/artifact.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
