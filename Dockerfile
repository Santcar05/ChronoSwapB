# --- Etapa 1: Build ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Aprovechamos el sistema de capas para las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
# Usamos flags para acelerar la compilación y reducir logs innecesarios
RUN mvn clean package -DskipTests -Dmaven.test.skip=true -Dspring-boot.repackage.skip=false

# --- Etapa 2: Runtime (Imagen Final) ---
# Usamos JRE en lugar de JDK y Alpine para minimizar el tamaño
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear un usuario de sistema para no ejecutar como root (Seguridad)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos solo el artefacto necesario
COPY --from=build /app/target/*.jar app.jar

# Optimización de rendimiento: Ajustes de memoria y recolección de basura
ENV JAVA_OPTS="-XX:+UseParallelGC -XX:MaxRAMPercentage=75.0 -XshowSettings:vm"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]