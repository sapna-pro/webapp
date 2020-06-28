#!bin/bash
echo "stop server"
sudo apt-get update && sudo apt-get install -yy less
sudo service tomcat8 stop