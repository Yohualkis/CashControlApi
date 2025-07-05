# Fase 1: Generacion del JAR
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copia los archivos de compilacion (Gradle/Maven)
COPY build.gradle.kts ./
COPY src ./src

# Instala dependencias y construye el JAR
RUN ./gradlew build --no-daemon

# Fase 2: Imagen final
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el JAR desde la fase de construcción
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Variables de entorno
ENV PORT=8080
EXPOSE $PORT

# Comando de inicio
CMD ["java", "-jar", "app.jar"]