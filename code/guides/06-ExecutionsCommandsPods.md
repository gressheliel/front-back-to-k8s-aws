## Ejecución de comandos en Pods

- Conectar con el cliente de aws:
  - deployment/aws$ aws configure
  - AWS Access Key ID [****************ABCD]: <your-access-key>
  - AWS Secret Access Key [****************ABCD]: <your-secret-key>
  - Default region name [us-east-1]: <your-region>
  - Default output format [json]: <your-output-format>

- Ejecuta las configuraciones de los archivos YAML en el directorio actual:
  .../deployment/aws$ kubectl apply -f secrets.yml
  .../deployment/aws$ kubectl apply -f configmap.yml
  .../deployment/aws$ kubectl apply -f eureka-statefulset-service.yml
  .../deployment/aws$ kubectl apply -f food-deployment-service.yml
  .../deployment/aws$ kubectl apply -f order-deployment-service.yml
  .../deployment/aws$ kubectl apply -f restaurant-deployment-service.yml
  .../deployment/aws$ kubectl apply -f user-deployment-service.yml

  .../deployment/aws$ kubectl apply -f .


- Lista los pods.
  - kubectl get pods
- Lista los servicios en el servicio con us detalles.
  - kubectl get svc
- Despliegue del log de algún pod.
  - kubectl log <pod-name>
- Lista los configmaps en el cluster
  - kubectl get configmap
- Lista los secrets en el cluster
  - kubectl get secret
- Lista los deployments en el cluster
  - kubectl get deployment
