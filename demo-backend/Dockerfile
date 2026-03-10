# --- Build du jar avec Maven (multi-stage) ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# --- Runtime minimal JRE 21 ---
FROM eclipse-temurin:21-jre
WORKDIR /app
# copie le jar généré (adapte le nom si besoin)
COPY --from=build /app/target/*.jar app.jar

# (optionnel) tuning JVM
ENV JAVA_OPTS=""

EXPOSE 8080
CMD ["sh","-c","java $JAVA_OPTS -jar app.jar"]
