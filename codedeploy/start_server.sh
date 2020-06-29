#!/bin/bash
cd /home/ubuntu/assignment/target/
kill -9 `pgrep -f webapp` 
chmod 775 assignment-0.0.1-SNAPSHOT.war
java -jar assignment-0.0.1-SNAPSHOT.war 