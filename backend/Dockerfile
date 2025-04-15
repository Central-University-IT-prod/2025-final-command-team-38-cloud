FROM gradle:8.12.1-jdk23-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM openjdk:23-slim
EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/app-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
