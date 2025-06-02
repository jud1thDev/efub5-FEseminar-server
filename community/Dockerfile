FROM openjdk:17-slim

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV RDS_IP=${RDS_IP}
ENV RDS_USERNAME=${RDS_USERNAME}
ENV RDS_PASSWORD=${RDS_PASSWORD}

ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","/app.jar"]
