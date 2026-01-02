FROM maven:3.8.4-openjdk-17 AS build

COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

RUN apt-get update \
    && apt-get install -y ca-certificates \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/url-shortener-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
