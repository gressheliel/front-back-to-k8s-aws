## ClusterIP
- ClusterIP es el tipo de servicio por defecto en Kubernetes. 
- Expone el servicio en una IP interna del clúster, accesible solo desde dentro del clúster (no desde fuera).  
- Se usa para comunicación interna entre pods y servicios.

- Si se requiere exponer el servicio fuera del clúster, 
  se debe usar otro tipo de servicio como NodePort o LoadBalancer.

- kubectl get services
  - Muestra los servicios en el clúster, incluyendo sus tipos (ClusterIP, NodePort, LoadBalancer).

## LoadBalancer
- Distribuye el tráfico entrante entre múltiples instancias de un servicio, 
  mejorando la disponibilidad y escalabilidad de las aplicaciones.
- Round Robin: distribuye las solicitudes de manera uniforme entre todas las instancias disponibles.

## Tipos de LoadBalancer
- Classic Load Balancer: 
  - Proporciona un balanceo de carga básico a nivel de capa 4 (TCP/UDP) y capa 7 (HTTP/HTTPS).
  - Adecuado para aplicaciones simples que no requieren características avanzadas.
- Application Load Balancer (ALB):
  - Opera a nivel de capa 7 (HTTP/HTTPS).
  - Ofrece características avanzadas como enrutamiento basado en contenido,
    soporte para WebSockets, y autenticación integrada.
  - Ideal para aplicaciones web modernas que requieren enrutamiento avanzado.
- Network Load Balancer (NLB):
  - Opera a nivel de capa 4 (TCP/UDP).
  - Diseñado para manejar grandes volúmenes de tráfico con baja latencia.
  - Adecuado para aplicaciones que requieren alta disponibilidad y rendimiento.

## AWs LoadBalancer en Kubernetes
- Servicio que ayuda a distribuir el tráfico de red entrante a múltiples pods en un clúster de Kubernetes.
- Proporciona alta disponibilidad y escalabilidad para aplicaciones desplegadas en Kubernetes.
- Actúa como un punto de entrada único para el tráfico externo, 
  dirigiendo las solicitudes a los pods adecuados según las reglas de balanceo de carga configuradas.

## Creación de un ALB Controller
- Revisar los ultimos
0. https://docs.aws.amazon.com/eks/latest/userguide/lbc-manifest.html
1. https://docs.aws.amazon.com/eks/latest/userguide/lbc-helm.html

2. https://www.youtube.com/watch?v=KSz9TTCzgLQ
3. https://github.com/RohanRusta21/eks-alb-ingress-controller
4. https://www.checkmateq.com/blog/ingress-controller

According to the AWS documentation, follow the steps below:
1. Conectar con el cliente de aws:
    - deployment/aws$ aws configure
    - AWS Access Key ID [****************ABCD]: <your-access-key>
    - AWS Secret Access Key [****************ABCD]: <your-secret-key>
    - Default region name [us-east-1]: <your-region>kubectl get deployment -n kube-system aws-load-balancer-controller
    - Default output format [json]: <your-output-format>

2. Run the following command to download the required IAM policy file:
  - curl -O https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.4.7/docs/install/iam_policy.json

3. Create an IAM policy named "AWSLoadBalancerControllerIAMPolicy" using the downloaded policy document in your AWS region 
  (e.g., us-east-1):
  - aws iam create-policy --policy-name AWSLoadBalancerControllerIAMPolicy --policy-document file://iam_policy.json --region us-east-1

4. Associate the IAM OIDC provider with your EKS cluster in your AWS region (e.g., us-east-1):
   (nombre del cluster aws --cluster=aws-eks-cluster)
  - eksctl utils associate-iam-oidc-provider --region=us-east-1 --cluster=aws-eks-cluster --approve

5. Create an IAM service account for the "aws-load-balancer-controller" in the "kube-system" namespace of your EKS cluster 
   (e.g., aws-eks-cluster) in your AWS region (e.g., us-est-1).
   Important: Ensure that you modify "782482296161" with your AWS account ID(915260181708), 
              replace "eu-west-3" with the appropriate AWS region,and substitute "aws-eks-cluster1" with the respective name of your AWS cluster.
   Use the appropriate AWS account ID and IAM policy ARN:
  - eksctl create iamserviceaccount --cluster=aws-eks-cluster --namespace=kube-system --region us-east-1 --name=aws-load-balancer-controller --role-name AmazonEKSLoadBalancerControllerRole --attach-policy-arn=arn:aws:iam::915260181708:policy/AWSLoadBalancerControllerIAMPolicy --approve 
  - eksctl create iamserviceaccount --cluster=aws-eks-cluster \
                                  --namespace=kube-system \
                                  --region us-east-1 \
                                  --name=aws-load-balancer-controller \
                                  --role-name AmazonEKSLoadBalancerControllerRole 
                                  --attach-policy-arn=arn:aws:iam::915260181708:policy/AWSLoadBalancerControllerIAMPolicy \
                                  --approve

6. Apply the Cert Manager YAML file for installation:
  - kubectl apply --validate=false -f https://github.com/jetstack/cert-manager/releases/download/v1.5.4/cert-manager.yaml

7. Download the YAML file for the AWS Load Balancer Controller from the following URL:
  - https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.4.7/docs/install/iam_policy.json
  
- Remove section ServiceAccount section from the downloaded YAML file:
```
apiVersion: v1
kind: ServiceAccount
metadata:
  labels:
    app.kubernetes.io/component: controller
    app.kubernetes.io/name: aws-load-balancer-controller
  name: aws-load-balancer-controller
  namespace: kube-system
```

- Modify the section
```
    spec:
      containers:
        - args:
            - --cluster-name=aws-eks-cluster
            - --ingress-class=alb
            - -aws-vpc-id=vpc-xxxxxxxx
            - -aws-region=us-east-1
          image: codedecode25/aws-load-balancer:v2.4.7
```
- To get aws-vpc-id=vpc-xxxxxxxx,  Go to AWS Cluster -> aws-eks-cluster -> Networking -> vpc-xxxxxxxxxxxx


Apply the edited YAML file:
  - kubectl apply -f v2_4_7_full_modified.yml


See the Load Balancer Controller pods:
  - kubectl get pods -n kube-system
    - NAME                                        READY   STATUS    RESTARTS   AGE
        aws-load-balancer-controller-7b9f5c6f7b-zzx8k   1/1     Running   0          10m    


## Capas del Modelo OSI
- Capa 1: Física - Transmisión de bits a través de un medio físico
- Capa 2: Enlace de Datos - Comunicación entre nodos en una red local
- Capa 3: Red - Enrutamiento de paquetes entre redes (IP)
- Capa 4: Transporte - Comunicación de extremo a extremo (TCP/UDP)
- Capa 5: Sesión - Gestión de sesiones entre aplicaciones
- Capa 6: Presentación - Traducción de datos entre formatos
- Capa 7: Aplicación - Interacción directa con el usuario final (HTTP,