<p align="center">
  <img src="https://github.com/ClementDv/OpCl-Java-P8/blob/dev/.readme/tourGuideLogo.PNG?raw=true" alt="Logo"/>
</p>

# OpCl-Java-P8

An application to travel and visit partner locations.
The goal is to rise the performance of the application. The tracker have problem to track a large amount of people.

## Increase Performance

The main goal of the project is to reach minimum performance to track users.
Before Tracking took at least 2hours.

New performance :
```
: Tracking progress : 96908/100000. [96.9%]. Time : 3m14s
: Tracking progress : 97416/100000. [97.4%]. Time : 3m15s
: Tracking progress : 97924/100000. [97.9%]. Time : 3m16s
: Tracking progress : 98424/100000. [98.4%]. Time : 3m17s
: Tracking progress : 98939/100000. [98.9%]. Time : 3m18s
: Tracking progress : 99456/100000. [99.5%]. Time : 3m19s
: Tracking progress : 99907/100000. [99.9%]. Time : 3m20s
: Tracking progress : 99987/100000. [100.0%]. Time : 3m21s
: Tracking progress : 99996/100000. [100.0%]. Time : 3m22s
: Tracking progress : 100000/100000. [100.0%]. Time : 3m23s
Tracking End. User(s) have been successfully tracked
```

## Prerequisites

1. [Java version 11](https://adoptopenjdk.net/?variant=openjdk15&jvmVariant=hotspot)
2. [Docker](https://docs.docker.com/get-docker/)
3. [Docker-compose](https://docs.docker.com/compose/install/)

## Run Services

Compile the application
```
./gradlew build
```

Run all services
```
java -jar users/build/libs/users.jar
java -jar gps/build/libs/gps.jar
java -jar rewards/build/libs/rewards.jar
```

## Docs

Click on the following links while the services are running

**Tourguide / users**: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

**gps** : http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

**rewards** : http://localhost:8082/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

## Deployement

Overview :

<p>
  <img src="https://github.com/ClementDv/OpCl-Java-P8/blob/master/.readme/Sch%C3%A9ma%20P8.png?raw=true" alt="Logo"/>
</p>

Deploy containers
```
docker-compose up --build -d
```

Stop deployement
```
docker-compose down
```

---------------------------------------
*Thank You.  
CLemDv*
