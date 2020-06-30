#!/bin/bash
cd /home/ubuntu/
source export.sh
cd /home/ubuntu/assignment/target/
kill -9 `pgrep -f assignment` 
chmod 775 assignment-0.0.1-SNAPSHOT.war
nohup java -jar assignment-0.0.1-SNAPSHOT.war & 