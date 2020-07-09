#!/bin/bash
cd /home/ubuntu/codedeploy
sudo chmod 775 your-application.service
sudo cp  your-application.service  /etc/systemd/system/
sudo chmod u+x your-script.sh
sudo systemctl daemon-reload
sudo systemctl enable your-application.service
sudo systemctl start your-application