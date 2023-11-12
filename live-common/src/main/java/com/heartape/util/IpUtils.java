package com.heartape.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * ip工具类
 */
public class IpUtils {

    /**
     * 获取本地IPv4地址
     * @return 本地IPv4地址
     */
    public static InetAddress localAddressV4(){
        return localAddressV4(null);
    }

    /**
     * 通过网卡名称，获取本地IPv4地址
     * @param networkInterfaceName 网卡名称
     * @return 本地IPv4地址
     */
    public static InetAddress localAddressV4(String networkInterfaceName){
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException | NullPointerException ignore) {
            return null;
        }
        Iterator<NetworkInterface> networkInterfaceIterator = networkInterfaces.asIterator();
        while (networkInterfaceIterator.hasNext()) {
            NetworkInterface networkInterface = networkInterfaceIterator.next();
            try {
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
            } catch (SocketException ignore) {
                continue;
            }
            if (networkInterfaceName != null && !networkInterface.getName().equals(networkInterfaceName)) {
                continue;
            }
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress.isLoopbackAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isMulticastAddress() || !inetAddress.isSiteLocalAddress() || inetAddress.getAddress().length != 4) {
                    continue;
                }
                return inetAddress;
            }
        }
        return null;
    }

}
