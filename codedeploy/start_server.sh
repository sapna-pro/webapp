#!/bin/bash

cd /home/ubuntu/
source export.sh
cd /home/ubuntu/assignment/target/
kill -9 `pgrep -f assignment` 
sudo chmod 775 assignment-0.0.1-SNAPSHOT.war
nohup sh -c "java -jar /home/ubuntu/assignment/target/assignment-0.0.1-SNAPSHOT.war > /dev/null 2>&1 &'