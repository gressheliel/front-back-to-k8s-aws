## Angular Frontend
- Modificar las URLs de los servicios en el front, para que apunten al Ingress,
  Que se definió en el paso anterior.
- En el componente constants :

```
export const API_URL_RL ='http://localhost:9091';
export const API_URL_Order ='http://localhost:9094';
export const API_URL_FC ='http://localhost:9092';
export const API_URL_UD ='http://localhost:9093';

export const K8ExternalIp = 'http://https://k8s-default-awsingre-9171d950d0-1317211627.us-east-1.elb.amazonaws.com';
```

## Compilación y despliegue del frontend
- Compilar la aplicación Angular para producción.
  - ng build
  - ng serve   => Los datos visualizados provienen de los servicios desplegados en Kubernetes.

- Creación de imagen docker
  - .../food-delivery-app$ docker build -t gresshel/food-delivery-app:0.0.1 .

- Push de la imagen a Docker Hub
  - .../food-delivery-app$ docker push gresshel/food-delivery-app:0.0.1

## Archivo de deployment para angular-frontend
- Crea una infraestructura en el cluster de Kubernetes para desplegar la aplicación angular-frontend.
- Consta de un Deployment y un Service.
- kubectl apply -f angular-deployment-service.yaml

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: angularapp
spec:
  replicas: 1  # Adjust the number of replicas as per your requirements
  selector:
    matchLabels:
      app: angularapp
  template:
    metadata:
      labels:
        app: angularapp
    spec:
      containers:
        - name: angular-app-container
          image: gresshel/food-delivery-app:0.0.1
          imagePullPolicy: Always
          ports:
            - containerPort: 80

---

apiVersion: v1
kind: Service
metadata:
  name: angular-service
spec:
  selector:
    app: angularapp
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80
```
