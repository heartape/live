package com.heartape.util.id.snowflake;

import com.heartape.exception.SystemInnerException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @see SnowflakeHolder
 */
@Slf4j
public class IpSnowflakeHolder implements SnowflakeHolder {

    private Long workerId;

    public IpSnowflakeHolder() {
        Long workerId = getWorkerId();
        if (workerId == null){
            throw new SystemInnerException();
        }
        this.workerId = workerId;
    }

    /**
     * 通过ip获取WorkerId
     */
    @Override
    public Long getWorkerId() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    log.debug("获取到ip:{}", inetAddress.getHostAddress());
                    if (inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isMulticastAddress() || !inetAddress.isSiteLocalAddress()) {
                        continue;
                    }
                    byte[] address = inetAddress.getAddress();
                    if (address.length == 4) {
                        Long id = (0x000000FF & (long) address[address.length - 2] | (0x0000FF00 & (((long) address[address.length - 1]) << 8))) >> 6;
                        log.debug("获取到workerId:{}", id);
                        return this.workerId = id;
                    }
                }
            }
        } catch (SocketException | NullPointerException e) {
            log.error("无法正确获取本机ip");
        }
        return this.workerId;
    }
}
