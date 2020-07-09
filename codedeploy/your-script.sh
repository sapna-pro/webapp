#!/bin/bash
cd /home/ubuntu/
sudo chown -R ubuntu:ubuntu /home/ubuntu/* 
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s -c file:/home/ubuntu/assignment/src/main/java/com/webapp/assignment/config.json
source export.sh
cd /home/ubuntu/assignment/target/
sudo chmod 775 assignment-0.0.1-SNAPSHOT.war
java -jar /home/ubuntu/assignment/target/assignment-0.0.1-SNAPSHOT.war > /home/ubuntu/webapplog.txt
