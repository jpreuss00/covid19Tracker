FROM openjdk:13-alpine
WORKDIR /
ADD build/libs/covid19Tracker.jar Covid19Tracker.jar
CMD ["java", "-jar", "Covid19Tracker.jar", "--port=$PORT"]
