# nttdata-service

Servicio para registrar moviemientos de clientes

## Dependencias

- [Docker](https://www.docker.com/) v18.09.+



##### Instalación de docker:
[Aquí](https://docs.docker.com/install/linux/docker-ce/ubuntu/)


##### Ejecutar aplicación local (`http://localhost:8080/`):

Cambiar en el aplication.yml 

ESTO:
spring:
  application:
    name: local
  profiles:
    include:
      - info
  datasource:
    url: jdbc:postgresql://host.docker.internal:49153/nttdata
    username: postgres
    password: postgrespw
POR ESTO:
spring:
  application:
    name: local
  profiles:
    include:
      - info
  datasource:
    url: jdbc:postgresql://127.0.0.1:5432/nttdata
    username: postgres
    password: postgres



Crear una base de datos postgres llamada nttdata







##### Ejecutar aplicación Dockerfile (`http://localhost:8080/`):

Cambiar en el aplication.yml 

ESTO:
spring:
  application:
    name: local
  profiles:
    include:
      - info
  datasource:
    url: jdbc:postgresql://host.docker.internal:49153/nttdata
    username: postgres
    password: postgrespw
POR ESTO:
spring:
  application:
    name: local
  profiles:
    include:
      - info
  datasource:
    url: jdbc:postgresql://127.0.0.1:5432/nttdata
    username: postgres
    password: postgres

Iniciar docker : Correr como administrador Docker Desktop

Crear una base de datos postgres llamada nttdata

Ejecutar cmd como administrador en la raíz de proyecto
 
	docker build -t nttdata .
	
	docker run --network host --name nttdata-service-container nttdata-service

Nota: El contenedor accederá directamente a la base local





##### Ejecutar aplicación docker-compose.yml (`http://localhost:80/`):
Levantar postgres cómo contenedor [Aquí https://hub.docker.com/_/postgres]

Iniciar docker : Correr como administrador Docker Desktop

Iniciar Postgres como contenedor: Correr postgresSQL en Docker Desktop

Ejecutar cmd como administrador en la raíz de proyecto
	
	Ejecutar cmd como administrador en la raíz de proyecto

		- docker run -p 5050:80 -e 'PGADMIN_DEFAULT_EMAIL=pgadmin4@pgadmin.org' -e 'PGADMIN_DEFAULT_PASSWORD=admin' -d --name pgadmin4 dpage/pgadmin4
		
		- docker exec -it postgres-88hR psql -U postgres
				
			-postgres=# create database nttdata;

			-\q

		-docker compose up		

	
Nota: El contenedor accederá directamente a la base creada como contenedor





