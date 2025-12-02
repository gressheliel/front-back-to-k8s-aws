## Archivo de deployment para configmap
- Crea infraestructura en kubernetes para almacenar variables de entorno 
  y configuraciones que pueden ser utilizadas por los pods.
- Permite separar la configuración del código de la aplicación, facilitando 
  la gestión y actualización de las configuraciones sin necesidad de 
  reconstruir las imágenes de los contenedores.

- En los archivos de deployment de los microservicios y los profiles de los 
  microservicios(profile aws), se hace referencia a las variables:
  - ${SPRING_DATASOURCE_URL}
  - ${SPRING_DATA_MONGODB_URI}
  - ${SPRING_DATASOURCE_PASSWORD}
  - ${SPRING_DATASOURCE_USERNAME}
  
- Se reemplazan por las variables definidas en el configmap.
  Ejemplo :
```
            - name: SPRING_DATA_MONGODB_URI
              valueFrom:
                configMapKeyRef:
                  name: configmap
                  key: orderdb_url
```



```
apiVersion: v1
kind: ConfigMap
metadata:
  name: configmap
data:
  restaurantdb_url: jdbc:mysql://mysql-db-instance.cabgywiecjty.us-east-1.rds.amazonaws.com:3306/restaurantdb
  foodcataloguedb_url: jdbc:mysql://mysql-db-instance.cabgywiecjty.us-east-1.rds.amazonaws.com:3306/foodcataloguedb
  userdb_url: jdbc:mysql://mysql-db-instance.cabgywiecjty.us-east-1.rds.amazonaws.com:3306/userdb
  orderdb_url: mongodb+srv://NEWMAIL:PWDNEWMAIL@clusteratlasproject.veoy0yy.mongodb.net/?retryWrites=true&w=majority&appName=ClusterAtlasProject
  eureka_service_address: http://eureka-0.eureka-service:8761/eureka

```