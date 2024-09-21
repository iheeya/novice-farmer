FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR = /app

COPY . .

RUN ls -al

RUN pwd
# Gradle 빌드
RUN rm -rf .gradle

RUN chmod +x ./gradlew && ./gradlew clean build
RUN ls -al ./build/libs/
RUN cp ./build/libs/farmer-0.0.1-SNAPSHOT.jar ./build/libs/farmer.jar

RUN ls -al ./build/libs/
RUN cp ./build/libs/farmer.jar ./app.jar
RUN ls -al
#COPY ./build/libs/farmer.jar ./app.jar

ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./app.jar"]

