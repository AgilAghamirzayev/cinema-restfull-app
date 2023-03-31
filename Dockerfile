FROM openjdk:17-alpine as build
MAINTAINER agil.aghamirzayev
COPY target/cinema-restfull-app-0.0.1-SNAPSHOT.jar cinema-rest-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/cinema-rest-app-0.0.1-SNAPSHOT.jar"]