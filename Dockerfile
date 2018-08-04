FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/spending-service-0.1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} spending-service.jar

# Run the jar file
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/spending-service.jar" ]
