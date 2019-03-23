# Dockerfile created for the RapidMiner home assignment by Eshan Mushtaq (Guide : https://codefresh.io/docker-tutorial/java_docker_pipeline/)

# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
MAINTAINER Author eshanmushtaq@gmail.com

# copy application WAR (with libraries inside)
COPY target/csv-parser-*.war /app.war
# specify default command
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]