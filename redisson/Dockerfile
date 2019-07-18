FROM hub.c.163.com/library/java:8-alpine

MAINTAINER noly 1346735074@qq.com

ADD target/*.jar redisson.jar

EXPOSE 7000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "/redisson.jar"]