# Fase de construcción (JDK)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1. Copia los archivos de Gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# 2. Descarga dependencias (caché eficiente)
RUN ./gradlew dependencies --no-daemon

# 3. Copia el código fuente y construye
COPY src ./src
RUN ./gradlew build --no-daemon

# Fase de ejecución (JRE - más liviano)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el JAR desde la fase de construcción
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Puerto dinámico para Render
ENV PORT=8080
EXPOSE $PORT

# Comando de inicio
CMD ["java", "-jar", "app.jar"]