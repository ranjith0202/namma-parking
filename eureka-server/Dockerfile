FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/*.jar app.jar
ENV JAVA_OPTS="-Dhibernate.jdbc.time_zone=Asia/Kolkata"
ENTRYPOINT ["java","-jar","app.jar"]