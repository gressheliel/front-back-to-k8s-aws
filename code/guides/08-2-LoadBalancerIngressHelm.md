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
p  - Proporciona un balanceo de carga básico a nivel de capa 4 (TCP/UDP) y capa 7 (HTTP/HTTPS).
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

## AWS LoadBalancer en Kubernetes
- Servicio que ayuda a distribuir el tráfico de red entrante a múltiples pods en un clúster de Kubernetes.
- Proporciona alta disponibilidad y escalabilidad para aplicaciones desplegadas en Kubernetes.
- Actúa como un punto de entrada único para el tráfico externo, 
  dirigiendo las solicitudes a los pods adecuados según las reglas de balanceo de carga configuradas.

## Creación de un ALB Controller
- https://github.com/RohanRusta21/eks-alb-ingress-controller
- Utilizando Helm, se desplegó el backend completo

## Associates an OIDC provider with your EKS cluster
- Associates an OIDC provider with your EKS cluster
- Allows IAM roles to be used inside the Kubernetes cluster.(
  - UPDATE! Value of region
  - eksctl utils associate-iam-oidc-provider --cluster alb-demo-cluster  --approve --region us-east-2

## Step 1: Create IAM Role using eksctl
- Download an IAM policy for the AWS Load Balancer Controller that allows it to make calls to AWS APIs.
- Since the AWS Load Balancer Controller needs permissions to manage AWS Load Balancers, 
  you need to create an IAM policy and associate it with a Kubernetes service account.
  - curl -O https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.11.0/docs/install/iam_policy.json

- Create an IAM policy using the policy downloaded in the previous step.
  - Creates a new IAM policy named AWSLoadBalancerControllerIAMPolicy.
  - This policy allows the Load Balancer Controller to make AWS API calls.
  - aws iam create-policy \
    --policy-name AWSLoadBalancerControllerIAMPolicy \
    --policy-document file://iam_policy.json

- Set up an IAM service account in an EKS cluster, allowing the AWS Load Balancer Controller 
  to manage AWS Load Balancers on behalf of the Kubernetes cluster.
  - Creates a Kubernetes ServiceAccount named aws-load-balancer-controller.
  - Associates it with an IAM Role (AmazonEKSLoadBalancerControllerRole).
  - Attaches the AWSLoadBalancerControllerIAMPolicy.
  - Allows Kubernetes to use AWS IAM for authentication.
  - UPDATE! Value of region
  - Ensure that you modify "<aws-account-id>" with your AWS account ID(915260181708),
  - eksctl create iamserviceaccount \
    --cluster=alb-demo-cluster \
    --namespace=kube-system \
    --name=aws-load-balancer-controller \
    --role-name AmazonEKSLoadBalancerControllerRole \
    --attach-policy-arn=arn:aws:iam::<aws-account-id>:policy/AWSLoadBalancerControllerIAMPolicy \
    --region us-east-2 \
    --approve


## Step 2: Install AWS Load Balancer Controller
- Add the eks-charts Helm chart repository.
  - helm repo add eks https://aws.github.io/eks-charts

- Update your local repo to make sure that you have the most recent charts (If Not first time).
  - helm repo update eks

- Install the AWS Load Balancer Controller.
  - Installs the AWS Load Balancer Controller in the kube-system namespace.
  - Links it to the existing aws-load-balancer-controller service account.
  - helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
    -n kube-system \
    --set clusterName=alb-demo-cluster \
    --set serviceAccount.create=false \
    --set serviceAccount.name=aws-load-balancer-controller

- If you use helm upgrade, you must manually install the CRDs.
  - wget https://raw.githubusercontent.com/aws/eks-charts/master/stable/aws-load-balancer-controller/crds/crds.yaml
    kubectl apply -f crds.yaml


## Step 3: Verify that the controller is installed
- kubectl get deployment -n kube-system aws-load-balancer-controller

## Modify  ingressHelm.yml, with the subnet-id1, subnet-id2
- Login AWS -> EC2 -> Instances -> Checked cluster node1 -> Copy Subnet ID
```
  annotations:
    alb.ingress.kubernetes.io/scheme: internet-facing
    alb.ingress.kubernetes.io/subnets: subnet-03c3b614d39c7b6d2, subnet-079500f68bb98e5e2 # Modify this as it will be different
```
- kubectl apply -f ingressHelm.yml
- kubectl get ingress








## Capas del Modelo OSI
- Capa 1: Física - Transmisión de bits a través de un medio físico
- Capa 2: Enlace de Datos - Comunicación entre nodos en una red local
- Capa 3: Red - Enrutamiento de paquetes entre redes (IP)
- Capa 4: Transporte - Comunicación de extremo a extremo (TCP/UDP)
- Capa 5: Sesión - Gestión de sesiones entre aplicaciones
- Capa 6: Presentación - Traducción de datos entre formatos
- Capa 7: Aplicación - Interacción directa con el usuario final (HTTP,