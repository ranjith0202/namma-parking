FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV JAVA_OPTS="-Dhibernate.jdbc.time_zone=Asia/Kolkata"

ENTRYPOINT ["java","-jar","/app.jar"]