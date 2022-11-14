FROM maven:3.8.6-jdk-11-slim as build-bank-service
WORKDIR /build/bankservice

COPY pom.xml ./pom.xml

RUN mvn dependency:resolve

COPY ../.. .

RUN sed -i 's+localhost:1433+bank-mssql-db:1433+g' src/main/resources/application.yml

RUN mvn install

RUN mkdir /bank-serive && \
    cp target/bank-service-*.jar /bank-serive/bank-serive.jar

FROM openjdk:11-jre
COPY --from=build-bank-service /bank-serive /bank-serive
WORKDIR /bank-serive
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8787", "-jar", "bank-serive.jar"]