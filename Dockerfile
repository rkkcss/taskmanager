FROM ubuntu:latest AS build
RUN apt-get update && apt-get install -y maven
COPY . .
RUN ./mvnw package

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /build/libs/demo-1.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
