FROM openjdk:19-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /home/server.jar
ENTRYPOINT ["java","-jar","/home/server.jar"]