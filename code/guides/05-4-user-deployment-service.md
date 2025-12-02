## Archivo de deployment para user-service
- Crea una infraestructura en el cluster de Kubernetes para desplegar la aplicación user-service.
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
kind: Deployment
metadata:
  name: userapp
  labels:
    app: userapp
spec:
  replicas: 1
  selector:
    matchLabels:
      app: userapp
  template:
    metadata:
      labels:
        app: userapp
    spec:
      containers:
        - name: userapp
          image: gressheliel/user-service:0.0.1
          imagePullPolicy: Always
          ports:
            - containerPort: 9093
          env:
            - name: SPRING_DATASOURCE_USERNAME
              valueFrom:
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
                  key: userdb_url
---
apiVersion: v1
kind: Service
metadata:
  name: user-service
spec:
  ports:
    - protocol: TCP
      port: 9093
      targetPort: 9093
  selector:
    app: userapp
```