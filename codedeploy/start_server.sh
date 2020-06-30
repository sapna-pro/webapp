#!/bin/bash
cd /home/ubuntu/
source export.sh
cd /home/ubuntu/assignment/target/
kill -9 `pgrep -f webapp` 
chmod 775 assignment-0.0.1-SNAPSHOT.war
Nohup java -jar assignment-0.0.1-SNAPSHOT.war & 