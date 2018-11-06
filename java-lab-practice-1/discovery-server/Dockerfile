FROM openjdk:8-jdk-alpine

LABEL maintainer="sema.melnikov@gmail.com"

VOLUME /tmp

EXPOSE 9000

ARG JAR_FILE=target/discovery-server-1.0.jar

ADD ${JAR_FILE} discovery-server-1.0.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/discovery-server-1.0.jar"]