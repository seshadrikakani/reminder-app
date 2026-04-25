# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

# 🔥 Skip test compilation completely
RUN mvn clean package -Dmaven.test.skip=true

# Run stagegit add .
           #git commit -m "final docker fix"
           #git push
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]