# Documentation

Microservice used to Check Infra behaviour.

- `/ping`: ping pong
- `/sleep`: request sleep for X milliseconds
- `/doerror`: throw error (HttpStatus.NOT_IMPLEMENTED)
- `/exit`: call `System.exit(0);`
- `/memory`: stress memory until OOM

# Usage

The following guides illustrate how to use some app (https://httpie.io/):

```
http localhost:8080/ping
http localhost:8080/sleep?time=3000
http localhost:8080/doerror
http localhost:8080/exit
http localhost:8080/memory
```

# Java Memory

`-XX:+ExitOnOutOfMemoryError`
> When you enable this option, the JVM exits on the first occurrence of an out-of-memory error. It can be used if you prefer restarting an instance of the JVM rather than handling out of memory errors.

## K8S

### K8S flag -XX:+ExitOnOutOfMemoryError (disbled)

```yml
        resources: 
          requests:
            memory: 128Mi
            cpu: 250m
          limits:
            memory: 256Mi
            cpu: 500m
        env:
          - name: JAVA_OPTS
            #value: -Xms50M -Xmx100M -XX:+ExitOnOutOfMemoryError
            value: -Xms50M -Xmx100M
```

Java throws `java.lang.OutOfMemoryError: Java heap space` but the container does not go down. You can keep calling API's after the error.

```sh
kubectl apply -f deployment.yaml

# Open other 2 tabs:
kubectl logs -f pods/infrack-deployment-...
watch kubectl get all

http <ip>:8080/memory
http <ip>:8080/memory
```

### K8S flag -XX:+ExitOnOutOfMemoryError (enabled)

```yml
        resources: 
          requests:
            memory: 128Mi
            cpu: 250m
          limits:
            memory: 256Mi
            cpu: 500m
        env:
          - name: JAVA_OPTS
            value: -Xms50M -Xmx100M -XX:+ExitOnOutOfMemoryError
            #value: -Xms50M -Xmx100M
```

Java throws `java.lang.OutOfMemoryError: Java heap space` and the container **goes down** (because flag `-XX:+ExitOnOutOfMemoryError` is enabled).

```sh
kubectl apply -f deployment.yaml

# Open other 2 tabs:
kubectl logs -f pods/infrack-deployment-...
watch kubectl get all

http <ip>:8080/memory
# Output: Terminating due to java.lang.OutOfMemoryError: Java heap space
```

## Docker

### Without flag -XX:+ExitOnOutOfMemoryError

Java throws `java.lang.OutOfMemoryError: Java heap space` but the container does not go down. You can keep calling API's after the error.

```sh
docker run --rm -ti -p 8080:8080 --memory=256m -e JAVA_OPTS="-Xms50M -Xmx50M" bproenca/infrack:s3j17azul
http localhost:8080/memory
http localhost:8080/memory
```

### With flag -XX:+ExitOnOutOfMemoryError

Java throws `java.lang.OutOfMemoryError: Java heap space` and the container **goes down** (because flag `-XX:+ExitOnOutOfMemoryError` is enabled).

```sh
docker run --rm -ti -p 8080:8080 --memory=256m -e JAVA_OPTS="-Xms50M -Xmx50M -XX:+ExitOnOutOfMemoryError" bproenca/infrack:s3j17azul
http localhost:8080/memory

# Output: Terminating due to java.lang.OutOfMemoryError: Java heap space
```

### Container Memory < Java Memory

Container is terminated because can't allocate more memory (it's not a: `java.lang.OutOfMemoryError: Java heap space`).

```sh
docker run --rm -ti -p 8080:8080 --memory=100m -e JAVA_OPTS="-Xms50M -Xmx256M" bproenca/infrack:s3j17azul
http localhost:8080/memory
```