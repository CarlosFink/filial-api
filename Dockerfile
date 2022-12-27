FROM maven:3.8.1-openjdk-17-slim AS MAVEN_BUILD
ENV APP_HOME=/workspace
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
COPY . $APP_HOME
RUN --mount=type=cache,target=/root/.m2 mvn -f $APP_HOME/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-alpine
COPY --from=MAVEN_BUILD /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]