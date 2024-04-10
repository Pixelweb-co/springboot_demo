# Usa la imagen de Amazon Corretto con la versión específica de Java que necesites
FROM amazoncorretto:8-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Instala herramientas necesarias
RUN apk --no-cache add curl tar bash

# Descarga Maven
RUN curl -fsSL -o /tmp/apache-maven.zip https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip && \
    unzip -q /tmp/apache-maven.zip -d /usr/share && \
    rm -f /tmp/apache-maven.zip && \
    ln -s /usr/share/apache-maven-3.9.6 /usr/share/maven

# Establece la variable de entorno PATH para Maven
ENV PATH="/usr/share/maven/bin:${PATH}"

# Copia los archivos de la aplicación al contenedor
COPY ./. .

# Excluye la carpeta .git
RUN rm -rf ./.git

# Compila el proyecto con Maven
RUN mvn clean package

# Expone el puerto en el que tu aplicación Spring Boot está escuchando
EXPOSE 8080

# Comando para ejecutar tu aplicación Spring Boot cuando el contenedor se inicia
CMD ["java", "-jar", "target/users-0.0.1-SNAPSHOT.jar"]
