FROM openjdk
COPY build/libs/usuario-0.0.1-SNAPSHOT.jar Transaction-Microservice.jar
CMD sleep 15 && java -jar Transaction-Microservice.jar