FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar",".app.jar"]
EXPOSE 9090


#FROM maven:3-openjdk-17 AS build
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM maven:3-openjdk-17-slim
#COPY --from=build /target/CrimeRecordManagementSystem-0.0.1-SNAPSHOT.jar CrimeRecordManagementSystem.jar
#EXPOSE 9090
#ENTRYPOINT ["java","-jar","CrimeRecordManagementSystem.jar"]