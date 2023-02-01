#!/bin/bash
tag="s3j17azul"

echo "## Package JAR"
./mvnw clean package

echo "## RMI $tag"
docker rmi bproenca/infrack:$tag

echo "## Build docker image"
docker build -f Dockerfile -t bproenca/infrack:$tag .
