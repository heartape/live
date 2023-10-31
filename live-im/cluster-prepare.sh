yum -y install openssh-server

# 修改配置文件 /etc/ssh/sshd_config
# 以下为ssh需要打开的配置
# Port 22
# ListenAddress 0.0.0.0
# ListenAddress ::
# PermitRootLogin yes
# PasswordAuthentication yes
