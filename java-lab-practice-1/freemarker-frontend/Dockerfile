FROM openjdk:8-jdk-alpine

LABEL maintainer="sema.melnikov@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/freemarker-frontend-1.0.jar

ADD ${JAR_FILE} freemarker-frontend-1.0.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/freemarker-frontend-1.0.jar"]