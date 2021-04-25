#!/usr/bin/env bash
echo "开始进行后端构建"
mvn package
echo "构建完毕，开始上传"
scp /home/mola/IdeaProjects/molablogv2/target/molablogv2-0.0.1.jar mola@120.27.230.24:/home/mola/molablog-app;