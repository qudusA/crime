FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080


#FROM maven:3.8.4-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean install
#
#FROM openjdk:17-alpine
#COPY --from=build /target/CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar ./CrimeRecordManagementSystem.jar
#EXPOSE 9090
#ENTRYPOINT ["java","-jar","CrimeRecordManagementSystem.jar"]