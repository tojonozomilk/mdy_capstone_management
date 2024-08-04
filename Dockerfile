# Build stage
FROM maven:3.8.5-openjdk-8 AS build-stage
WORKDIR /app
COPY . .
# 配置aliyun Maven 镜像
#COPY config/settings.xml /root/.m2/settings.xml
RUN mvn clean package

# Runtime stage
FROM openjdk:8-jdk-slim
WORKDIR /app
COPY --from=build-stage /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
