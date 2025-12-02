## Archivo de deployment para food-service
- Crea una infraestructura en el cluster de Kubernetes para desplegar la aplicación food-service.
- Consta de un Deployment y un Service.
- En kubernetes la unidad más pequeña que se puede desplegar es un Pod, 
  pero en la práctica no se crean Pods directamente, sino que se utilizan objetos de más alto nivel 
  como Deployments, que gestionan la creación y el ciclo de vida de los Pods.
- El deployment define los replicas (Pods) que se desean, la imagen del contenedor a utilizar, 
  las variables de entorno necesarias para la configuración de la aplicación, 
  y cómo se deben exponer los puertos.
- Deployment: administra y actualiza los pods.
- Service: expone y balancea el acceso a los pods.


```
apiVersion: apps/v1
kind: Deployment # Kubernetes resource kind we are creating
metadata:
  name: foodcatalogueapp
  labels:
    app: foodcatalogueapp
spec:
  replicas: 1 # Number of replicas that will be created for this deployment
  selector:
    matchLabels:
      app: foodcatalogueapp
  template:
    metadata:
      labels:
        app: foodcatalogueapp
    spec:
      containers:
        - name: foodcatalogueapp
          image: gresshel/food-service:0.0.1
          imagePullPolicy: Always
          ports:
            - containerPort: 9092 #

          env: # Environment variables supplied to the Pod
            - name: SPRING_DATASOURCE_USERNAME # Name of the environment variable
              valueFrom: # Get the value of environment variable from kubernetes secrets
                secretKeyRef:
                  name: secret
                  key: mysql-username
            - name: SPRING_DATASOURCE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: secret
                  key: mysql-password
            - name: SPRING_DATASOURCE_URL
              valueFrom:
                configMapKeyRef:
                  name: configmap
                  key: foodcataloguedb_url

---

apiVersion: v1
kind: Service
metadata:
  name: foodcatalogue-service
spec:
  ports:
    - protocol: TCP
      port: 9092
      targetPort: 9092
  selector:
    app: foodcatalogueapp
```