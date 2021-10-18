FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY build/libs/patientmanagementapi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]

