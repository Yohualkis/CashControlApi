# Fase de construcción
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copia archivos necesarios
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# Descarga dependencias
RUN ./gradlew dependencies --no-daemon

# Copia código fuente
COPY src ./src

# Construye el JAR
RUN ./gradlew build --no-daemon

# Fase de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el jar generado
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Puerto y comando de ejecución
ENV PORT=8080
EXPOSE $PORT
CMD ["java", "-jar", "app.jar"]
