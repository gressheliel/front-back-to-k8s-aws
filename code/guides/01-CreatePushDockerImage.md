## Docker Hub
- (gresshel@gmail.com, elieta103)

## Agregar Dockerfile en cada project
- Construir en la raíz de cada proyecto los archivos Dockerfile

## Comandos para crear imágenes.
- code$ docker login
- code$ docker-compose up -d

- code/eureka-service$ ./mvnw clean package
- code/eureka-service$ docker build -t gresshel/eureka-service:0.0.1 .
- code/eureka-service$ docker images => gresshel/eureka-service
- code/eureka-service$ docker push gresshel/eureka-service:0.0.1

- code/food-service$ ./mvnw clean package -DskipTests
- code/food-service$ docker build -t gresshel/food-service:0.0.1 .
- code/food-service$ docker image => gresshel/food-service
- code/food-service$ docker push gresshel/food-service:0.0.1

- code/order-service$ ./mvnw clean package -DskipTests
- code/order-service$ docker build -t gresshel/order-service:0.0.1 .
- code/order-service$ docker image => gresshel/order-service
- code/order-service$ docker push gresshel/order-service:0.0.1

- code/restaurant-service$ ./mvnw clean package -DskipTests
- code/restaurant-service$ docker build -t gresshel/restaurant-service:0.0.1 .
- code/restaurant-service$ docker images => gresshel/restaurant-service
- code/restaurant-service$ docker push gresshel/restaurant-service:0.0.1

- code/user-service$ ./mvnw clean package -DskipTests
- code/user-service$ docker build -t gresshel/user-service:0.0.1 .
- code/user-service$ docker images => gresshel/user-service
- code/user-service$ docker push gresshel/user-service:0.0.1

- food-delivery-app$ docker build -t gresshel/food-delivery-app:0.0.1 .
- food-delivery-app$ docker push gresshel/food-delivery-app:0.0.1
