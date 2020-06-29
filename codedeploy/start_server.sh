#!/bin/bash
cd /home/ubuntu/assignment/target/
kill -9 `pgrep -f webapp` 
java -jar assignment-0.0.1-SNAPSHOT.war 