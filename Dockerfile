# Use a base image with OpenJDK 21 installed
FROM openjdk:21-jdk-slim

ARG JAR_FILE=build/libs/price-update-notification-service*SNAPSHOT.jar
RUN apt-get update

# Copy the Spring Boot JAR file into the container
COPY build/libs/price-update-notification-service*SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8020

#ADD wait-for-kafka.sh wait-for-kafka.sh
#RUN chmod +x wait-for-kafka.sh
# Define the command to run the application
#ENTRYPOINT ["./wait-for-kafka.sh", "kafka:9092", "--","java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]