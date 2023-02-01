### Documentation

- `/ping`: ping pong
- `/sleep`: request sleep for X milliseconds
- `/doerror`: throw error (HttpStatus.NOT_IMPLEMENTED)
- `/exit`: call `System.exit(0);`
- `/memory`: stress memory until OOM

### Start

```
docker run --rm -ti -p 8080:8080 --memory=256m -e JAVA_OPTS="-Xms50M -Xmx50M -XX:+ExitOnOutOfMemoryError" bproenca/infrack:s3j17azul
docker run --rm -ti -p 8080:8080 --memory=256m -e JAVA_OPTS="-Xms50M -Xmx50M -XX:+ExitOnOutOfMemoryError" -e MY_ACTUATOR="info" bproenca/infrack:s3j17azul

docker run --rm -ti -p 8080:8080 --memory=100m -e JAVA_OPTS="-Xms50M -Xmx256M -XX:+ExitOnOutOfMemoryError" bproenca/infrack:s3j17azul
```

### Usage

The following guides illustrate how to use some app (https://httpie.io/):

```
http localhost:8080/ping
http localhost:8080/sleep?time=3000
http localhost:8080/doerror
http localhost:8080/exit
http localhost:8080/memory
```

### Java Arguments

`-XX:+ExitOnOutOfMemoryError`
> When you enable this option, the JVM exits on the first occurrence of an out-of-memory error. It can be used if you prefer restarting an instance of the JVM rather than handling out of memory errors.
