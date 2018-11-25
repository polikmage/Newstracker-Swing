FROM openjdk:8
ADD target/news-tracker.jar news-tracker.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","news-tracker.jar"]