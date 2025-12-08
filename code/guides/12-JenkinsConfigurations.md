## Configuración de Jenkins
- Jenkins es una herramienta de automatización de código abierto que facilita la integración continua y la entrega continua (CI/CD) en proyectos de software. A continuación, se describen los pasos básicos para configurar Jenkins en tu entorno.
- Status de Jenkins
  - sudo systemctl status jenkins
- Acceder a la Interfaz Web de Jenkins
  - http://3.82.59.116:8080/login?from=%2F
- Acceder al Archivo de Configuración de Jenkins
  - sudo nano /var/lib/jenkins/secrets/initialAdminPassword
  - cec938773fc24873aed40ea9f7f262ff
  
- Instalar Plugins Recomendados
- Crear el Primer Usuario Administrador
- Guardar URL de Jenkins
- Se accede a la Interfaz Web de Jenkins

## Instalación de Plugins SSH y MAVEN
- Plugin de Maven es para la compilación, 
- Plugin SSH es para la conexión segura a GitHub (Cuando se actualizan las versiones de las imágenes Docker en el repositorio GitOps)
- Se instala el plugin SSH para permitir la conexión segura a servidores remotos desde Jenkins.
- Buscar con la Lupa , que esta del lado derecho superior
- Navegar a Manage Jenkins -> Plugins -> Available plugins -> Buscar SSH Agent -> Install without restart
- Navegar a Manage Jenkins -> Tools -> Maven Installations -> Add Maven -> Escribir Name : Maven ->  Save


## Integrar el Webhook de GitHub con Jenkins
- Se configura un webhook en GitHub para que Jenkins pueda recibir notificaciones de eventos, como push o pull requests, y desencadenar trabajos de construcción automáticamente.
- Se crea un nuevo proyecto de Maven con restaurante-service llamado:
  - front-back-to-k8s-aws-restaurant-service-cicd

- Desde el repositorio de GitHub:
  - Navegar a Settings -> Webhooks -> Add webhook -> Pide Authentication Token
  - Payload URL: http://3.82.59.116:8080/github-webhook/  [Debe ser la URL de Jenkins en EC2 seguida de /github-webhook/ Termina con barra / ]
  - Content type: application/json
  - Events: Just the push event.
  - Add webhook

- Crear Credenciales SSH en Jenkins
  - ssh-keygen -t rsa -b 4096 [Valores default ó enter]
  - Se guarda la clave privada en el servidor Jenkins y la clave pública se agrega a las claves SSH del repositorio de GitHub.
  - Your identification has been saved in cat /home/ec2-user/.ssh/id_rsa
- Se deben recuperar las claves generadas:
    - La Llave publica es para GitHub:
    - cat /home/ec2-user/.ssh/id_rsa.pub
```
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDbe+/eRkvZhdKjDKvdbArEpvJyZce+s7rhLPg8cpywG7cVkYKSiwRVZNKYUxL56Dc2j2+icLdllGhuK9K7t8HjPoZil+zMNEyK6GririiOejn7XfHLou3XCYBVCrAR3nadMZ+9Ub6h2S81fnD5xp9GlW270TMVBOYOiH3ELqnp6ddNEy4zDQVknd6dydhdfyz0BrbaMj1wHtKQjiQUt2erMpMAPxIm1fZ+OlsSw4xLmWaUCVZQNpjNXMCWEz8jep9xVVOX3jwjLnyNa/oDLTRF26tjkgIMT5LMWQSz6f/eRGnI5+XDxKsGXeLyFddcQqOoMSQbEG9GeKg1z+KwsCJ/2NYGlxAs7sj3Mil8OPxK9PEH42a4roKne/Yw5sdpEHMVE9KpKtIrMFdAsi94UOx4c+hCB/w3CIPPaZlAu4gSSOE0shIayT0iIpK7rzYMbE4HXEJ7OFHGL8It8abY/3TqFdJKKkUekg+D2yKjrzmznKaL/K+CPsArDo5tnb55iI2e606i/Dw2ih0zkym+xVJsDjjzt9RndbEsg9Rj39YLBvmdklp/Ljbblp/0ssRLRgZ2qKWauzd/x8AIxPjxA3Sw5DBMghbOSVmrCE0Cfyg/3wB02i/kfiEdabzEO/sPlXL6ohMXo4PfVfeCSB6ZvJfOcRTwaayZj/7wxU3IMjKCIw== ec2-user@ip-172-31-35-252.ec2.internal
```
    - La Llave privada es para Jenkins:
    - cat /home/ec2-user/.ssh/id_rsa
```
-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAACFwAAAAdzc2gtcn
TFGoh+A7f5hrAAAAJmVjMi11c2VyQGlwLTE3Mi0zMS0zNS0yNTIuZWMyLmludGVybmFsAQ
IDBA==
-----END OPENSSH PRIVATE KEY-----
```
- Desde github se agrega la clave pública, raíz de gresshel:
  - Navegar a Settings -> SSH and GPG keys -> New SSH key -> Title: jenkins-public-key -> Key: [Pegar la clave pública generada arriba] -> Add SSH key
- Desde Jenkins se agrega la clave privada:
  - Navegar a Manage Jenkins -> Credentials -> System -> Global credentials (unrestricted)
  - Add Credentials -> Kind: SSH Username with private key -> Scope Global(Jenkins, nodes, items, all child items) 
  - ID: git-ssh -> Private Key: Enter directly -> [Pegar la clave privada generada arriba] -> Create

## Añadir Credenciales en Jenkins para Docker
- Navegar a Manage Jenkins -> Credentials -> System -> Global credentials (unrestricted)
- Add Credentials -> Kind: Username with password -> Scope Global(Jenkins, nodes, items, all child items) 
- ID: docker-hub -> Username: gresshel -> Password: [DockerHubPassword] -> Create

## Deshabilitar verificación de git push, en la seguridad de Jenkins
- Manage Jenkins -> Security -> Git Host key Verification Configuration -> No verification
- Manda warning, pero funciona
- Guardar Cambios

## Crear un Nuevo Job en Jenkins
- Icono Jenkins -> Add Item -> Enter an item name : restaurant-listing-pipeline -> Pipeline -> OK
- Build Triggers -> GitHub hook trigger for GITScm polling -> Save
- Configure -> Pipeline -> Pipeline script from SCM
- SCM: Git
- Repository URL: https://github.com/gressheliel/front-back-to-k8s-aws-restaurant-service-cicd.git
- Branch Specifier: main
- Guardar Cambios


## Jenkinsfile Creacion
- Crear un archivo llamado `Jenkinsfile` en el directorio raíz del proyecto Maven.
- Agregarlo al repositorio GitHub del proyecto.

## Build del Job en Jenkins
- Desde la Interfaz Web de Jenkins, navegar al Job creado: restaurant-listing-pipeline
- Hacer clic en "Build Now" para iniciar el proceso de construcción.
- Monitorear la consola de salida para verificar que el proceso se complete sin errores.

## Error por permisos de Docker en Jenkins
- Si se encuentra un error relacionado con permisos al ejecutar comandos Docker desde Jenkins, es probable que
- el usuario Jenkins no tenga los permisos adecuados para acceder al socket de Docker.
- Solución:
- Conceder permisos al usuario Jenkins para acceder al socket de Docker:
  - sudo usermod -aG docker jenkins
  - Reiniciar el servicio Jenkins para aplicar los cambios:
  - sudo systemctl restart jenkins ó
  - sudo service jenkins restart

## Verificar funcionamiento
- Se generan la imagen Docker en DockerHub : gresshel/restaurant-service:16
- En el repositorio GitHub : front-back-to-k8s-aws-deployment-cicd/aws/restaurant-deployment-service.yml
  - También se actualiza la imagen a la versión 16
  - Esta acción es realizada por el Jenkinsfile, en el stage: stage('Update Image Tag in GitOps')
  
```
...
    spec:
      containers:
        - name: restaurantapp
          image: gresshel/restaurant-listing-service:16
          imagePullPolicy: Always
          ports:
...
```