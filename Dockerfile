## Runtime JRE

FROM adoptopenjdk/openjdk15:alpine-jre as java

## Create app user
RUN addgroup -g 1000 app && \
    adduser -h /var/lib/app -u 1000 -G app -D app && \
    mkdir -p /app && \
    chown app:app /app
USER app
WORKDIR /app

CMD ["java", "-jar", "tourguide.jar"]


FROM java as user
COPY users/build/libs/users.jar tourguide.jar
EXPOSE 8080

FROM java as gps
COPY gps/build/libs/gps.jar tourguide.jar
EXPOSE 8081

FROM java as rewards
COPY rewards/build/libs/rewards.jar tourguide.jar
EXPOSE 8082


