## Creación de una instancia EC2 para SonarQube
- Crear una instancia EC2 en AWS con las siguientes especificaciones:
  - Región: us-east-1 (Norte de Virginia)
  - Tipo de instancia: t2.large
  - Sistema operativo: Amazon Linux
  - Arquitectura : x86_64
  - Key pair: Seleccionar un par de claves existente para acceso SSH
  - Configuración de red: Usar una VPC y subred existentes
  - Asignación de IP pública: Habilitada
  - Habilitar: SSH, HTTP, HTTPs.
  - Crear instancia.

## Habilitar puertos para SonarQube y Jenkins
- En la instancia EC2 creada, configurar el grupo de seguridad para permitir el tráfico entrante en los siguientes puertos:
- Security Groups:
  - Puerto 22: Para acceso SSH
  - Puerto 9000: Para acceso a SonarQube
  - Puerto 8080: Para acceso a Jenkins

## Instalaciones necesarias en la instancia EC2
- Maven
  - sudo yum update
  - sudo yum install -y maven
  - mvn -version
- Java JDK 21
  - sudo yum install -y java-21-amazon-corretto-devel
  - java -version
- Docker
  - sudo yum update -y
  - sudo yum install -y docker
  - sudo service docker start
  - sudo chkconfig docker on
  - sudo usermod -aG docker ec2-user
  - docker version
  - Salir de la sesión SSH y volver a conectarse para aplicar los cambios de grupo.
- Jenkins
  - sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
  - sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
  - sudo yum install -y jenkins
  - sudo systemctl enable jenkins
  - sudo systemctl start jenkins
- Git
  - sudo yum install -y git
  - git --version

## Imagen de SonarQube con Docker
- docker run -d -p 9000:9000 --name sonarqube sonarqube
- docker logs -f sonarqube

## Acceso a SonarQube
- Acceder a SonarQube en el navegador web usando la IP pública de la instancia EC2 y el puerto 9000 
- (http://<EC2_PUBLIC_IP>:9000)
- Credenciales por defecto:
  - Usuario: admin
  - Contraseña: admin
- Cambiar la contraseña al primer inicio de sesión.
  - (admin / La de Siempre con @ y con Sonar)
  
## Configuración inicial de SonarQube Token
- Generar un token de autenticación
- Navegar a "My Account" > "Security"
- Crear un nuevo token del tipo : User Token,  Sin Fecha de expiración
  squ_fab382c024ee0fa5f18d35d62fa158811aca7f29

## Quality Gates
- Navegar a "Quality Gates" en el menú principal
- Crear un nuevo Quality Gate llamado "SonarRules"
- Agregar condición 
  - Seleccionando "On Overall Code"
  - Quality Gate fails when
  - Coverage < 80%
- Agregar condición
  - Seleccionando "On Overall Code"
  - Quality Gate fails when
  - Line Coverage < 80%
- Una vez creado el Quality Gate, establecerlo como predeterminado haciendo clic en el botón "Set as Default".
- Esto asegurará que todos los proyectos analizados utilicen este Quality Gate a menos que se especifique lo contrario.
- Si no aparece "Set as Default", Hacer click en los tres puntos verticales al lado del Quality Gate y seleccionar "Set as Default".

## Informe del SonarQube, MS Restaurant
- Editar el archivo pom.xml del proyecto Maven para incluir las siguientes configuraciones:		
```
...
<!-- Agregar las siguientes lineas dentro de la etiqueta <properties> -->
<sonar.exclusions>**/com/elhg/restaurant/dto/** , **/*/com/elhg/restaurant/entity/**/*</sonar.exclusions>
...
<!-- Agregar las siguientes lineas dentro de la etiqueta <plugins> -->
			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
				<version>0.8.8</version>
				<executions>
					<execution>
						<id>prepare-agent</id>
						<goals>
							<goal>prepare-agent</goal>
						</goals>
					</execution>
					<execution>
						<id>report</id>
						<goals>
							<goal>report</goal>
						</goals>
					</execution>
				</executions>
			</plugin>

```
## Ejecutar el analisis de codigo statico con SonarQube 
- Ejecutar el siguiente comando Maven en la terminal dentro del directorio del proyecto Maven:
- .../front-back-to-k8s-aws/code/restaurant-service
```
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://3.82.59.116:9000 -Dsonar.login=squ_fab382c024ee0fa5f18d35d62fa158811aca7f29
```

## Resultado del analisis en SonarQube
- Navegar a SonarQube en el navegador web usando la IP pública de la instancia EC2 y el puerto 9000 
- (http://<EC2_PUBLIC_IP>:9000)
- Iniciar sesión con las credenciales de administrador
- Buscar el proyecto analizado en el panel de control
