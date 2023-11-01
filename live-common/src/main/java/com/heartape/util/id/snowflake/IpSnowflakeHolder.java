package com.heartape.util.id.snowflake;

import com.heartape.exception.SystemInnerException;
import com.heartape.util.IpUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * @see SnowflakeHolder
 */
@Slf4j
public class IpSnowflakeHolder implements SnowflakeHolder {

    private Long workerId;

    /**
     * 需要时可以指定网卡名称
     */
    private final String networkInterfaceName;

    public IpSnowflakeHolder(String networkInterfaceName) {
        this.networkInterfaceName = networkInterfaceName;
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
        InetAddress inetAddress = IpUtils.localAddressV4(networkInterfaceName);
        if (inetAddress == null){
            return this.workerId;
        }
        log.debug("获取到ip:{}", inetAddress);
        byte[] address = inetAddress.getAddress();
        Long id = (0x000000FF & (long) address[address.length - 2] | (0x0000FF00 & (((long) address[address.length - 1]) << 8))) >> 6;
        log.debug("获取到workerId:{}", id);
        return this.workerId = id;
    }
}
