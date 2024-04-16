FROM eclipse-temurin:17-alpine
LABEL maintaner="Manuel Cardenas <card.edu.10@gmail.com>"
LABEL version="1.0"
LABEL description="Product Service"
WORKDIR /app
COPY src src
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN /bin/sh mvnw clean package -P docker -DskipTests
ENTRYPOINT ["java", "-jar", "/app/target/product-service-0.0.1.jar"]
