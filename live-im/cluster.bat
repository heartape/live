@echo off

%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

ssh root@192.168.31.101 "mkdir -p /opt/im;systemctl start sshd"
scp -r target/live-im.jar root@192.168.31.101:/opt/im/live-im.jar
start powershell -Command ssh root@192.168.31.101 "source /etc/profile;java -jar /opt/im/live-im.jar"

ssh root@192.168.31.102 "mkdir -p /opt/im;systemctl start sshd"
scp -r target/live-im.jar root@192.168.31.102:/opt/im/live-im.jar
start powershell -Command ssh root@192.168.31.102 "source /etc/profile;java -jar /opt/im/live-im.jar"

ssh root@192.168.31.103 "mkdir -p /opt/im;systemctl start sshd"
scp -r target/live-im.jar root@192.168.31.103:/opt/im/live-im.jar
start powershell -Command ssh root@192.168.31.103 "source /etc/profile;java -jar /opt/im/live-im.jar"
