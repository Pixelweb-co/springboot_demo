Para instalar aplicacion:

- Tener java 17
- maven v3+

Modo local:

ejecute: 

mnv clean install
java -jar users-0.0.1-SNAPSHOT.jar &


Modo docker:


mnv clean install
docker-compose up -d

Abrir en POST http:localhost:8080/api/users

