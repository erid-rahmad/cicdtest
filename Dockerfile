#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
#COPY pom.xml /tmp/
#RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
#COPY src /tmp/src/
#WORKDIR /tmp/
#RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package
#
#FROM java:8-jre-alpine
#
#EXPOSE 8080
#
#RUN mkdir /app
#COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /app/spring-boot-application.jar
#
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]
#
#HEALTHCHECK --interval=1m --timeout=3s CMD wget -q -T 3 -s http://localhost:8080/actuator/health/ || exit 1

FROM openjdk:8
EXPOSE 8101

RUN mkdir logs
RUN mkdir config
COPY "/target/spring-boot-application.jar" "/app/spring-boot-application.jar"
COPY "/target/classes/application.yaml" "/config/application.yaml"
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]


