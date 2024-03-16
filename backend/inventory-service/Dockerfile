FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

# We assume the entire backend directory is mounted or copied in a monorepo approach.
# However, to keep it simple, we will build from the module directory and assume 
# renstation-common is available in the local .m2 cache, or we build it first.
# Since we can't easily guarantee common is built in this isolated Dockerfile without a parent POM,
# we will use the standard approach, BUT in our docker-compose we will volume mount ~/.m2 or build common first.

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Download dependencies
RUN ./mvnw dependency:go-offline || true
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
