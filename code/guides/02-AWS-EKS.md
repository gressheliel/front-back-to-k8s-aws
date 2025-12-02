## AWS Access
- (gressheliel@gmail.com, LaDeSiempreCon@103)

## Administrar llaves de acceso
- IAM Dashboard -> My Security Credentials -> Create Access Key -> Download Key File
- Manda advertencia, pero como soy el único no hay problema 
- Generate Access Key -> Download Key File CSV
  (AKIA*********, 38ooi***********)

## Instalar herramientas AWS-CLI, EKSCTL, KUBECTL
- Instalar kubectl
  - https://greenwebpage.com/community/how-to-install-kubectl-on-ubuntu-24-04/
- Instalar aws cli
  - $sudo snap install aws-cli --classic
- Instalar eksctl
  - $curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
  - $sudo mv /tmp/eksctl /usr/local/bin
  - $eksctl version

## Validar acceso AWS
- ...deployment/aws$ aws configure
  - AWS Access Key ID [None]: AKIA***************
  - AWS Secret Access Key [None]: 38oo*********************
  - Default region name [None]: us-east-1
  - Default output format [None]: json
- aws sts get-caller-identity                                                                                                                                                                             

## Crear cluster
 - eksctl create cluster --name aws-eks-cluster --region us-east-1 --nodegroup-name eks-cluster-node --node-type t3.medium --nodes 2
 - Desde la interfaz de AWS se puede ver el cluster creado o en proceso de creación.

## Tareas que se hacen con la creación del cluster

- Cluster Conﬁguration:.
- Node Group Creation: eksctl provisions the speciﬁed node group(s) within the EKS
  cluster. This involves launching EC2 instances or Fargate pods as worker nodes
  that join the cluster. The command sets up the necessary conﬁguration, such as
  instance types, instance proﬁles, and scaling options.
- kubeconﬁg Update: Once the cluster, control plane, and node groups are created,
  eksctl updates the kubeconﬁg ﬁle on your local machine. The kubeconﬁg ﬁle is
  conﬁgured with the necessary authentication details, cluster endpoint, and other
  conﬁgurations required to connect to the EKS cluster using tools like kubectl.
- IAM Role Creation: eksctl creates an IAM role for the EKS cluster's control plane.
  This role grants necessary permissions to manage the cluster and its resources.Tasks done by this simple command
- VPC Creation: If a VPC is not already available, eksctl creates a new Amazon Virtual
  Private Cloud (VPC) with the required subnets, routing, and security groups. This
  VPC will be used for the EKS cluster's networking.
- Control Plane Provisioning: Control plane is a master node. The command
  provisions the EKS control plane, which manages the cluster's resources,
  networking, and scaling. eksctl interacts with the Amazon EKS service to create and
  conﬁgure the control plane components.
- Cluster Veriﬁcation: After the cluster creation process, eksctl performs veriﬁcation
  checks to ensure the cluster is successfully provisioned and accessible. It conﬁrms
  that the nodes are running and communicating with the control plane..

## Archivo de configuración kubeconfig
- Se crea automáticamente en home/gresshel/.kube/config
- kubectl get nodes