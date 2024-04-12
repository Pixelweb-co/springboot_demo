FROM amazoncorretto:17-alpine

RUN apk add --no-cache maven

WORKDIR /usr/src/app

COPY pom.xml .
COPY src ./src
RUN mkdir target

EXPOSE 8080

RUN mvn install

CMD ["java", "-jar", "/usr/src/app/target/users-0.0.1-SNAPSHOT.jar"]
