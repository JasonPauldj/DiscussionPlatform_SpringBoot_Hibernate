FROM eclipse-temurin:17-jdk-jammy as codebase
WORKDIR /discussionplatform/app
COPY .mvn .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src/

FROM codebase as development
CMD ["./mvnw", "spring-boot:run"]

FROM codebase as build
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy as production
COPY --from=build /discussionplatform/app/target/DiscussionPlatform-0.0.1-SNAPSHOT.jar /app/DiscussionPlatform-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar" , "/app/DiscussionPlatform-0.0.1-SNAPSHOT.jar"]