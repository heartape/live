# live-im

即时通讯

## Getting Started

### springboot

```yaml
live:
  im:
    cluster:
      # 设定当前ip，用于集群注册
      host: 192.168.31.5
    # 使用网卡名称来指定当前ip
    network-interface-name: ens33
```

### startup-cluster-win

windows安装ssh客户端
```shell
Add-WindowsCapability -Online -Name OpenSSH.Client~~~~0.0.1.0
```

linux安装ssh服务端
```shell
yum -y install openssh-server
```

ssh服务端配置
```
# 修改配置文件 /etc/ssh/sshd_config
# 以下为ssh需要打开的配置
Port 22
ListenAddress 0.0.0.0
ListenAddress ::
PermitRootLogin yes
PasswordAuthentication yes
```

执行 `startup-cluster.bat`