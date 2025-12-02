## Archivo de deployment para eureka-service
- Crea una infraestructura en el cluster de Kubernetes para desplegar la aplicación eureka-service.
- Consta de un StatefulSet y un Service.
- Un StatefulSet en Kubernetes es un recurso que gestiona el despliegue y el escalado de pods con identidad estable 
  y persistente. A diferencia de un Deployment, los pods creados por un StatefulSet tienen:
  - Nombres únicos y ordenados.
  -  Volúmenes persistentes asociados a cada pod.
  - Orden de creación, actualización y eliminación controlado.
- Se usa para aplicaciones que requieren persistencia de datos y una identidad fija, como bases de datos o servicios de descubrimiento (por ejemplo, Eureka).
- Como los pods son efímeros y pueden ser eliminados y recreados, 
  el StatefulSet asegura que cada pod mantenga su identidad y datos a través de reinicios y actualizaciones.

## Diferencia entre las 2 URLs de Eureka
- La diferencia está en el nivel de resolución de nombres en Kubernetes:
- http://eureka-0.eureka-service.default.svc.cluster.local:8761/eureka 
  - Es la URL completa (FQDN) dentro del clúster. Incluye : 
    - El nombre del pod (eureka-0), 
    - El servicio (eureka-service), 
    - El namespace (default), 
    - Dominio interno (svc.cluster.local). 
  - Se usa para garantizar la resolución exacta y evitar ambigüedades entre namespaces.
- http://eureka-0.eureka-service:8761/eureka es una URL abreviada. 
  Kubernetes la resuelve dentro del mismo namespace (default). 
  - Es más corta y útil si todos los recursos están en el mismo namespace.
- Ambas apuntan al mismo destino si se usan dentro del namespace default, 
  pero la primera es explícita y la segunda depende del contexto de namespace.



```
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: eureka
spec:
  serviceName: eureka-service
  replicas: 1
  selector:
    matchLabels:
      app: eureka
  template:
    metadata:
      labels:
        app: eureka
    spec:
      containers:
        - name: eureka
          image: gresshel/eureka-service:0.0.1
          imagePullPolicy: Always
          ports:
            - containerPort: 8761
          env:
            - name: EUREKA_SERVER_ADDRESS
              valueFrom:
                configMapKeyRef:
                  name: configmap
                  key: eureka_service_address

---
apiVersion: v1
kind: Service
metadata:
  name: eureka-service
  labels:
    app: eureka-service
spec:
  ports:
    - protocol: TCP
      port: 8761
      targetPort: 8761
  selector:
    app: eureka
```