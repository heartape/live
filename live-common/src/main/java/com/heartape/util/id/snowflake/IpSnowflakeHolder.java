package com.heartape.util.id.snowflake;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @see SnowflakeHolder
 */
@Slf4j
public class IpSnowflakeHolder implements SnowflakeHolder {

    private Long workerId;

    public IpSnowflakeHolder() {
        this.workerId = getWorkerId();
    }

    /**
     * 通过ip获取WorkerId
     */
    @Override
    public Long getWorkerId() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            long id = (0x000000FF & (long) hardwareAddress[hardwareAddress.length - 2] | (0x0000FF00 & (((long) hardwareAddress[hardwareAddress.length - 1]) << 8))) >> 6;
            return this.workerId = id;
        } catch (SocketException | UnknownHostException | NullPointerException e) {
            log.error("无法正确获取本机ip");
            return this.workerId;
        }
    }
}
