FROM openjdk:8-alpine
ADD target/news-tracker.jar news-tracker.jar
ENTRYPOINT ["java","-jar","news-tracker.jar","--spring.config.location=/app/application.yml"]