#Base image with Java runtime
FROM openjdk:8-jdk-alpine

# Add jar file to container. JAR_FILE also provided as argument
ARG JAR_FILE='product-catalogue-service/target/*.jar'
ADD ${JAR_FILE} neo-service.jar

COPY start.sh /
RUN chmod +x /start.sh

CMD ["./start.sh"]