## Creación de Ingress en Kubernetes
- Ingress es un objeto de Kubernetes que gestiona el acceso externo a los servicios dentro del clúster, 
  típicamente a través de HTTP y HTTPS.
- Proporciona reglas para enrutar el tráfico a los servicios adecuados basándose en la URL
- Un ingress debe estar asociado a un controlador de ingress (ingress controller) 
  que implemente las reglas definidas en el recurso ingress.
- El Path debe ser el mismo que el definido en el servicio correspondiente. 
  - @RequestMapping("/user") etc.
- El name del service debe coincidir con el definido en el servicio correspondiente.
  - user-service etc.

## Aplicar ingress service, Comentando el path de angular
```
#          - path: /
#            pathType: Prefix
#            backend:
#              service:
#                name: angular-service
#                port:
#                  number: 80
```

- kubectl apply -f ingress.yaml
- kubectl get ingress



```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: aws-ingress
  #namespace: argocd
  annotations:
    kubernetes.io/ingress.class: alb
    alb.ingress.kubernetes.io/scheme: internet-facing
    alb.ingress.kubernetes.io/target-type: ip
spec:
  rules:
    - http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: angular-service
                port:
                  number: 80

          - path: /restaurant
            pathType: Prefix
            backend:
              service:
                name: restaurant-service
                port:
                  number: 9091

          - path: /foodCatalogue
            pathType: Prefix
            backend:
              service:
                name: foodcatalogue-service
                port:
                  number: 9092

          - path: /order
            pathType: Prefix
            backend:
              service:
                name: order-service
                port:
                  number: 9094


          - path: /user
            pathType: Prefix
            backend:
              service:
                name: user-service
                port:
                  number: 9093

```