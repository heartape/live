cd /d %~dp0
@echo off

%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

ssh root@192.168.31.101 "mkdir -p /opt/im;systemctl start sshd"
scp -r ./target/live-im.jar root@192.168.31.101:/opt/im/live-im.jar
start powershell -NoExit "ssh root@192.168.31.101 'kill $(lsof -t -i:8003);source /etc/profile;java -jar /opt/im/live-im.jar --live.im.network-interface-name=ens33' "

timeout /nobreak /t 5

ssh root@192.168.31.102 "mkdir -p /opt/im;systemctl start sshd"
scp -r ./target/live-im.jar root@192.168.31.102:/opt/im/live-im.jar
start powershell -NoExit "ssh root@192.168.31.102 'kill $(lsof -t -i:8003);source /etc/profile;java -jar /opt/im/live-im.jar --live.im.network-interface-name=ens33' "

timeout /nobreak /t 5

ssh root@192.168.31.103 "mkdir -p /opt/im;systemctl start sshd"
scp -r ./target/live-im.jar root@192.168.31.103:/opt/im/live-im.jar
start powershell -NoExit "ssh root@192.168.31.103 'kill $(lsof -t -i:8003);source /etc/profile;java -jar /opt/im/live-im.jar --live.im.network-interface-name=ens33' "
