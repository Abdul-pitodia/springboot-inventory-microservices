FROM eclipse-temurin:17.0.4.1_1-jre as build

WORKDIR extracted

ADD target/*.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM build as dev

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

