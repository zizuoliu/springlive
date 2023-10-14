FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} configservice.jar

ENTRYPOINT ["java", "-jar", "/configservice.jar"]

EXPOSE 8051