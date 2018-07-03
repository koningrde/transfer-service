# start with a light base image containing Java 8 Runtime
FROM openjdk:8-jdk-alpine

# maintainer info
LABEL maintainer="richard.dekoning@teamrockstars.nl"

# make port 8080 available to the world outside the container 
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/epayments-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} epayments.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/epayments.jar"]
