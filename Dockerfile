FROM openjdk:8-jdk-alpine
ARG JAR_FILE=api/target/*.jar
COPY api/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--persistenceTarget=postgres-docker"]