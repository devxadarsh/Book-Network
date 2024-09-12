# Build stage
# FROM maven:3.8.7-openjdk-18 AS build
# FROM maven:3.9.7-eclipse-temurin-21 AS build
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
# FROM amazoncorretto:21
# FROM gcr.io/distroless/java17-debian12
# FROM gcr.io/distroless/java21-debian12
FROM eclipse-temurin:21-jre-alpine
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/book-network-*.jar /app/

EXPOSE 8088

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV DB_URL=jdbc:postgresql://postgresql-sql-bsn:5432/book_social_network
# FOR CI/CD
ENV EMAIL_HOST_NAME=missing_hostname
ENV EMAIL_USER_NAME=missing_user_name
ENV EMAIL_PASSWORD=missing_password

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar
# CMD ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "-jar", "book-network-${JAR_VERSION}.jar"]

# docker build -t bsn/bsn:1.0.0 -f ../docker/backend/Dockerfile .
# docker-compose -f docker-compose.dev.yml up
# docker-compose -f my-app-config.yml up

# https://github.com/carlossg/docker-maven
# https://hub.docker.com/_/maven


