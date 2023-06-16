FROM eclipse-temurin:17-jdk-jammy as codebase
WORKDIR /discussionplatform/app
COPY .mvn .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src/

FROM codebase as development
ENV hibernate_host=""
ENV hibernate_connection_password=""
ENV hibernate_connection_username=""
ENV port=8080
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM codebase as build
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy as production
ENV hibernate_host=""
ENV hibernate_connection_password=""
ENV hibernate_connection_username=""
ENV port=8080
COPY --from=build /discussionplatform/app/target/DiscussionPlatform-0.0.1-SNAPSHOT.jar /app/DiscussionPlatform-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar" , "/app/DiscussionPlatform-0.0.1-SNAPSHOT.jar"]