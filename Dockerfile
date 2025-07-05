# Fase de construcción (build)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copia archivos necesarios para el build
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle
RUN chmod +x gradlew

# Descarga dependencias (mejora caché)
RUN ./gradlew dependencies --no-daemon

# Copia el código fuente
COPY src ./src

# Compila el proyecto sin correr tests
RUN ./gradlew build -x test --no-daemon

# Fase de ejecución (runtime)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el jar desde la fase anterior
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Expone el puerto que usará la app
ENV PORT=8080
EXPOSE $PORT

# Comando de inicio
CMD ["java", "-jar", "app.jar"]
