# ODB IT Applications

## Pre-requisites

1. Java 11
2. Maven 3.8.x


## Build

```shell
mvn clean install -DskipTests=true
```

## Run ZeroMQ App

```shell
mvn -pl odb-it-apps-zmq spring-boot:run -DskipTests=true
```

## Run RabbitMQ App

```shell
mvn -pl odb-it-apps-rabbitmq spring-boot:run -DskipTests=true

java -Dspring.profiles.active=LOCAL -jar target/odb-it-apps-rabbitmq-1.0.0.jar
```

## RabbitMQ console

```shell
http://localhost:15672/
```