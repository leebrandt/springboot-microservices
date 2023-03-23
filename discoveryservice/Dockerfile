FROM maven:3-openjdk-17 as build
WORKDIR /opt/code/src
COPY . .
RUN mvn package -DskipTests

FROM maven:3-openjdk-17-slim
COPY --from=build /opt/code/src/target/discoveryservice-0.0.1-SNAPSHOT.jar discoveryservice-0.0.1-SNAPSHOT.jar
CMD [ "java", "-jar", "discoveryservice-0.0.1-SNAPSHOT.jar" ]  