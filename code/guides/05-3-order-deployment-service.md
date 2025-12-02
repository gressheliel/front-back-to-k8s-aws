## Archivo de deployment para order-service
- Crea una infraestructura en el cluster de Kubernetes para desplegar la aplicación order-service.
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
  name: orderapp
spec:
  replicas: 1
  selector:
    matchLabels:
      app: orderapp
  template:
    metadata:
      labels:
        app: orderapp
    spec:
      containers:
        - name: orderapp
          image: gressheliel/order-service:0.0.1
          imagePullPolicy: Always
          ports:
            - containerPort: 9094
          env:
            - name: JAVA_TOOL_OPTIONS
              value: "-Djdk.tls.client.protocols=TLSv1.2"
            - name: SPRING_DATA_MONGODB_URI
              valueFrom:
                configMapKeyRef:
                  name: configmap
                  key: orderdb_url
            - name: SPRING_DATA_MONGODB_DATABASE
              value: "orderdb"

---
apiVersion: v1
kind: Service
metadata:
  name: order-service
spec:
  selector:
    app: orderapp
  ports:
    - protocol: TCP
      port: 9094
      targetPort: 9094
```