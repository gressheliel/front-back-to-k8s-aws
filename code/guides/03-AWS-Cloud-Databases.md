## AWS Access
- (gressheliel@gmail.com, LaDeSiempreCon@103)

## Crear DataBase RDS
- Seleccionar la misma región donde se creó el cluster EKS: --region us-east-1
- RDS -> Databases -> Create Database
- Standard Create -> Engine type: MySQL -> Version: 8.0.42 -> Templates: Free tier
- DB instance identifier: mysql-db-instance ->
- Master username: admin -> Master password: elieta103 -> Confirm password: elieta103
- Instance conﬁguration: DB instance class: db.t3.micro -> Storage: 20 GB
- Connectivity: Don't connect to an EC2 compute resource
- Public access: Yes -> 
- Additional conﬁguration: Database port: 3306 -> Initial database name: restaurantdb
- Default settings -> Create database
- Se crea la base de datos, puede tardar varios minutos.

- Mientras se crea la DB, modificar la interconexión, click en la BD creada 
  -> Connectivity & security -> VPC security groups -> Click en el SG
- Seleccionar Inbound rules -> Edit inbound rules
- Add rule -> Type: Custom TCP, Protocol: TCP, Port range: 3306, Source: Anywhere-IPv4 -> Save rules
- En Connectivity & security -> Endpoint: copiar el endpoint para usarlo después
  - Endpoint: mysql-db-instance.cabgywiecjty.us-east-1.rds.amazonaws.com
  - Port: 3306
  - Username: admin
  - Password: elieta103

- Una vez creado el host, se agregan las BD(foodcataloguedb, userdb) con su respectivas tablas y datos
