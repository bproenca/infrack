FROM azul/zulu-openjdk-alpine:17.0.11-17.50-jre-headless
COPY /target/*.jar /app/app.jar
ENV TZ=GMT-3
WORKDIR /app
CMD java ${JAVA_OPTS} -jar app.jar