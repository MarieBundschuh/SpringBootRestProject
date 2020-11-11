# SpringBootRestProject
SpringBoot Rest Tutorial From OpenClassRoomq

Swagger
http://localhost:9090/swagger-ui.html#/

# Adaptation pour test avec AWS

Création d'une base de donnée sur AWS RDS.
Maj de la datasource du projet.
Création d'un bucket sur AWS S3. Upload du jar sur le bucket.
Création d'une instance sur AWS EC2. Installation du jar sur le serveur.
Test du projet. 

http://15.188.41.228:9090/swagger-ui.html#/

# How to start the JAR file as a Service on Linux

Anyways, to create a Linux daemon the systemd way:

Create a service file in /etc/systemd/system. Let's call it javaservice.service. Let the contents be:

------ 

[Unit]
Description=My Java Service

[Service]
WorkingDirectory=/home/ec2-user
ExecStart=/usr/bin/java -Xmx256m -jar /home/ec2-user/microcommerce-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
User=ec2-user

[Install]
WantedBy=multi-user.target

------

sudo systemctl daemon-reload	<== notifies systemd of the new service file
sudo systemctl enable javaservice.service		<== enable it so it runs on boot
// javaservice.service will be added to the dir /etc/systemd/system/multi-user.target.wants. This dir indicates what services to start on boot

sudo systemctl start javaservice
sudo systemctl status javaservice   <== check that the service is running fine
