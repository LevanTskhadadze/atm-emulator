FROM maven:3.8.6-jdk-11-slim as build-atm-service
WORKDIR /build/atmservice

COPY pom.xml ./pom.xml

RUN mvn dependency:resolve

COPY ../.. .

RUN mvn install

RUN mkdir /atm-serive && \
    cp target/atm-service-*.jar /atm-serive/atm-serive.jar

FROM openjdk:11-jre
COPY --from=build-atm-service /atm-serive /atm-serive
WORKDIR /atm-serive
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8787", "-jar", "atm-serive.jar"]