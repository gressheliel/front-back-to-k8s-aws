## Archivo de deployment para secrets
- Crea infraestructura en Kubernetes para almacenar secretos de manera segura.
- Utiliza un Secret para almacenar credenciales de base de datos.
- Está en Base64. Se puede usar el comando `echo -n 'valor' | base64` para codificar.

- admin : YWRtaW4=
- elieta103 : ZWxpZXRhMTAz

```
apiVersion: v1
kind: Secret
metadata:
  name: secret
type: Opaque
data:
  mysql-username: YWRtaW4=
  mysql-password: ZWxpZXRhMTAz
```