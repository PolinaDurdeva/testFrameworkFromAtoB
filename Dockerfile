# Dockerfile
# STATUS: NOT READY
# Base image
FROM maven:3.6.3-jdk-8

# Make the folder as a working directory
WORKDIR /app

COPY pom.xml .

COPY testng.xml .

COPY . .

CMD ["mvn", "test"]