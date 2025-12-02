## ClusterIP
- ClusterIP es el tipo de servicio por defecto en Kubernetes. 
- Expone el servicio en una IP interna del clúster, accesible solo desde dentro del clúster (no desde fuera).  
- Se usa para comunicación interna entre pods y servicios.

- Si se requiere exponer el servicio fuera del clúster, 
  se debe usar otro tipo de servicio como NodePort o LoadBalancer.

## PortForwarding
- Permite redirigir el tráfico desde un puerto local a un puerto en un pod dentro
- Solo es una solución temporal para acceder a servicios en el clúster.
- No es adecuado para producción o acceso a gran escala.
- Posteriormente, se debe usar un Ingress o un servicio de tipo LoadBalancer 
  para exponer servicios de manera segura, escalable y poder ser consumido desde el frontend.

- Uso:
  - kubectl port-forward <pod-name> <local-port>:<pod-port>
  - Ejemplo:
    - kubectl port-forward service/eureka-service 8761:8761
    - Acceder a la aplicación en el navegador web o cliente HTTP usando http://localhost:8761

- kubectl port-forward service/eureka-service 8761:8761
- kubectl port-forward service/restaurant-service 9091:9091
- kubectl port-forward service/foodcatalogue-service 9092:9092
- kubectl port-forward service/user-service 9093:9093
- kubectl port-forward service/order-service 9094:9094
