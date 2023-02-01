echo "## Package JAR"
./mvnw clean package
echo "## Build docker image"
docker build -f Dockerfile -t bproenca/demo .
