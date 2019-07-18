#!/usr/bin/env bash
ELK_VERSION=latest

#mvn clean package -Dmaven.test.skip=true -U

docker build -t registry.cn-hangzhou.aliyuncs.com/engine/redisson:$ELK_VERSION .

docker push registry.cn-hangzhou.aliyuncs.com/engine/redisson:$ELK_VERSION

#docker run -e SPRING_PROFILES_ACTIVE="prod" -p 7000:7000 -d elk-engine:$ELK_VERSION




# 拉取
#docker pull registry.cn-hangzhou.aliyuncs.com/engine/redisson:$ELK_VERSION


# jar 启动
#java -jar /Volumes/CodeFile/GitHub/1346735074/elk-engine/es-scheduled/target/es-scheduled-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod