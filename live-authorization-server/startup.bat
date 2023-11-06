cd /d %~dp0
@echo off

%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

ssh root@192.168.31.100 "mkdir -p /opt/live;systemctl start sshd"
scp -r ./target/live-authorization-server.jar root@192.168.31.100:/opt/live/live-authorization-server.jar
scp -r ../rsa root@192.168.31.100:/root/rsa
start powershell -NoExit "ssh root@192.168.31.100 'kill $(lsof -t -i:8888);source /etc/profile;java -jar /opt/live/live-authorization-server.jar' "
