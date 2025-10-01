# Step 1: Use Maven to build the app
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run the app using JDK
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/tasklist-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
