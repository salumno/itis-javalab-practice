FROM openjdk:8-jdk-alpine

LABEL maintainer="sema.melnikov@gmail.com"

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=target/zuul-api-gateway-1.0.jar

ADD ${JAR_FILE} zuul-api-gateway-1.0.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zuul-api-gateway-1.0.jar"]