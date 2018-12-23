
FROM maven:3.6.0-alpine

WORKDIR /app

# First copy only the pom file. This is the file with less change
COPY pom.xml .

# Download the package and make it cached in docker image
RUN mvn dependency:resolve

# Copy the actual code -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml
COPY . .

# Then build the code -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml
RUN mvn package

# The rest is same as usual
EXPOSE 8082

CMD ["java", "-jar", "./target/news-tracker.jar"]