FROM openjdk:8-alpine
ADD target/news-tracker.jar news-tracker.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","news-tracker.jar","--spring.config.location=/app/application.yml"]