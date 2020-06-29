#!/bin/bash
cd /home/ubuntu/assignment/target/
sudo kill -9 `pgrep -f webapp` 
sudo java -jar assignment-0.0.1-SNAPSHOT.war 