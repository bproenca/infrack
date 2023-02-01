FROM azul/zulu-openjdk-alpine:17-jre-headless
COPY /target/*.jar /app/app.jar
ENV TZ=GMT-3
WORKDIR /app
CMD java ${JAVA_OPTS} -jar app.jar