FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/inditex-0.0.1-SNAPSHOT.jar app.jar
ENV MYSQL_URL=jdbc:mysql://mysql:3306/inditex
ENV MYSQL_USERNAME=root
ENV MYSQL_PASSWORD=root
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar"]