# This file is used to build a docker image for HttpSessionExampleApp
# You can create and upload image to docker hub via:
# ./gradlew clean build docker dockerPush
# 
# - or -
# Build new docker image commands:
#
# docker build . -t httpsessionexampleapp:v1
# docker tag httpsessionexampleapp:v1 kkellner/httpsessionexampleapp:v1
# docker push kkellner/httpsessionexampleapp:v1
#

FROM openjdk:8-jdk-alpine
LABEL maintainer="SpringBoot Session Example App"

VOLUME /tmp
COPY build/libs/HttpSessionExampleApp.jar app.jar 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8080
