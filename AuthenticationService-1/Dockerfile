FROM openjdk:8
ARG JARFILE=target/*.jar
COPY ${JARFILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8082:8082