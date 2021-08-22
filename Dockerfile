FROM maven:3.8.1-jdk-11-slim as builder
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src src
RUN mvn package -DskipTests

FROM adoptopenjdk:15-jre-hotspot
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/vizsgaremek-0.0.1-SNAPSHOT.jar vizsgaremek.jar
ENTRYPOINT ["java", "-jar", "vizsgaremek.jar"]