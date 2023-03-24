FROM openjdk:19

# Set the working directory to /app
WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/person-0.0.1-SNAPSHOT.jar"]

