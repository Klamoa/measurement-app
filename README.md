## Running the application
````shell
kubectl apply -f infrastructure/kubernetes/.
````
Needed for Docker Desktop as there is no Ingress Controller installed by default!
````shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.9.1/deploy/static/provider/cloud/deploy.yaml
````

## Useful commands to set up and run Kubernetes (Docker Desktop)

### Build projects and docker

Build all modules:
```shell
mvn clean package -am -DskipTests
```

Build specific module:
```shell
mvn clean package -pl measurement-service -am -DskipTests
```
```shell
mvn clean package -pl http-collector-service -am -DskipTests
```
```shell
mvn clean package -pl http-client -am -DskipTests
```

Build Docker Image
```shell
docker build -f measurement-service/src/main/docker/Dockerfile.jvm -t klamoa/measurement-service ./measurement-service
```
```shell
docker build -f http-collector-service/src/main/docker/Dockerfile.jvm -t klamoa/http-collector-service ./http-collector-service
```
```shell
docker build -f http-client/src/main/docker/Dockerfile.jvm -t klamoa/http-client ./http-client
```
```shell
docker build -f measurement-frontend/docker/Dockerfile -t klamoa/measurement-frontend .\measurement-frontend\
```