#DOCKERFILE MULTI STAGES
FROM openjdk:16-jdk-alpine AS builder
LABEL maintainer="maintainer@company.com"
WORKDIR application
COPY . .
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw --projects swagger-aggregator --also-make clean install -DskipTests

FROM openjdk:16-jdk-alpine AS runner
COPY --from=builder /application/swagger-aggregator/target/*.jar /application/backend-swagger-aggregator.jar
EXPOSE 8282
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/application/backend-swagger-aggregator.jar"]