#!/bin/bash

cd /home/ubuntu/
source export.sh
cd /home/ubuntu/assignment/target/
sudo chown -R ubuntu:ubuntu /home/ubuntu/* 
kill -9 `pgrep -f assignment` 
sudo chmod 775 assignment-0.0.1-SNAPSHOT.war
nohup java -jar /home/ubuntu/assignment/target/assignment-0.0.1-SNAPSHOT.war > /home/ubuntu/webapplog.txt 2> /home/ubuntu/webapplog.txt < /home/ubuntu/webapplog.txt & 
# nohup sh -c 'java -jar /home/ubuntu/assignment/target/assignment-0.0.1-SNAPSHOT.war > /dev/null 2>&1 &'