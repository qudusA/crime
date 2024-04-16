#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar"]
#EXPOSE 9090


FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar CrimeRecordManagementSystem.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","CrimeRecordManagementSystem.jar"]