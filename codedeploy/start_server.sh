#!/bin/bash

cd /home/ubuntu/
sudo chown -R ubuntu:ubuntu /home/ubuntu/* 
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s -c file:/home/ubuntu/assignment/src/main/java/com/webapp/assignment/config.json
source export.sh
cd /home/ubuntu/assignment/target/
kill -9 `pgrep -f assignment` 
sudo chmod 775 assignment-0.0.1-SNAPSHOT.war
nohup java -jar /home/ubuntu/assignment/target/assignment-0.0.1-SNAPSHOT.war > /home/ubuntu/webapplog.txt 2> /home/ubuntu/webapplog.txt < /home/ubuntu/webapplog.txt & 
# nohup sh -c 'java -jar /home/ubuntu/assignment/target/assignment-0.0.1-SNAPSHOT.war > /dev/null 2>&1 &'
# sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -m ec2 -a start