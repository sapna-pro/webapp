#!/bin/bash
cd /home/ubuntu/codedeploy
cp  your-application.service  /etc/systemd/system/
sudo chmod u+x your-script.sh
sudo systemctl daemon-reload
sudo systemctl enable your-application.service
sudo systemctl start your-application