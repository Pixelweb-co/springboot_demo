# Usa la imagen de Amazon Corretto con la versión específica de Java que necesites
FROM amazoncorretto:17-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR compilado de tu aplicación al contenedor
COPY target/users-0.0.1-SNAPSHOT.jar .

# Expone el puerto en el que tu aplicación Spring Boot está escuchando
EXPOSE 8080

# Establece la variable de entorno para el perfil de Spring
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar tu aplicación Spring Boot cuando el contenedor se inicia
CMD ["java", "-jar", "users-0.0.1-SNAPSHOT.jar"]
