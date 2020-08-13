FROM openjdk:11

RUN apt-get update && apt-get install -y maven
WORKDIR /home
COPY . .


ENTRYPOINT ["mvn", "spring-boot:run"]