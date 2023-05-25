FROM openjdk
COPY build/libs/transaction-0.0.1-SNAPSHOT.jar transaction.jar
CMD sleep 15 && java -jar transaction.jar