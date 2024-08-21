# Étape 1 : Utiliser une image de base Java
FROM openjdk:21-jdk-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR construit dans le conteneur
COPY target/*.jar app.jar

# Exposer le port que l'application écoute
EXPOSE 8080

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
