FROM openjdk:11.0.6-jre-slim-buster
VOLUME /tmp
ARG JAR_FILE
WORKDIR /var/thaalam/cloudMappers
COPY mappers /var/thaalam/cloudMappers
COPY thaalam-spring-boot/build/libs/thaalam-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
