### Documentation

- `/ping`: ping pong
- `/sleep`: request sleep for X milliseconds
- `/error`: throw error (HttpStatus.NOT_IMPLEMENTED)
- `/exit`: call `System.exit(0);`
- `/memory`: stress memory until OOM

### Start

docker run --rm -ti -p 8080:8080 --memory=256m -e JAVA_OPTS="-Xms50M -Xmx50M" bproenca/demo
docker run --rm -ti -p 8080:8080 --memory=50m -e JAVA_OPTS="-Xms256M -Xmx256M" bproenca/demo

### Usage

The following guides illustrate how to use some app (https://httpie.io/):

```
http localhost:8080/ping
http localhost:8080/sleep?time=3000
http localhost:8080/error
http localhost:8080/exit
http localhost:8080/memory
```