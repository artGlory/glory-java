package com.glory.gloryUtils.utils;


import java.net.*;
import java.util.Enumeration;

/**
 * IP相关工具类
 */
public class IpUtil {
    /**
     * InetAddress.getLocalHost()
     *
     * @return
     */
    public static InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本机指定IPv4对应的InetAddress
     *
     * @param ip
     * @return
     */
    public static Inet4Address getInet4Address(String ip) {
        if (ip == null || "".equals(ip.trim())) return null;
        Enumeration<NetworkInterface> networkInterfaceEnumeration = null;
        try {
            networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        NetworkInterface networkInterface = null;
        while (networkInterfaceEnumeration.hasMoreElements()) {
            networkInterface = networkInterfaceEnumeration.nextElement();
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            InetAddress inetAddress = null;
            while (inetAddressEnumeration.hasMoreElements()) {
                inetAddress = inetAddressEnumeration.nextElement();
                if (inetAddress instanceof Inet4Address && ip.equals(inetAddress.getHostAddress())) {
                    return (Inet4Address) inetAddress;
                }
            }
        }
        return null;
    }

    /**
     * 获取本机指定IPv6对应的InetAddress
     *
     * @param ip
     * @return
     */
    public static Inet6Address getInet6Address(String ip) {
        if (ip == null || "".equals(ip.trim())) return null;
        Enumeration<NetworkInterface> networkInterfaceEnumeration = null;
        try {
            networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        NetworkInterface networkInterface = null;
        while (networkInterfaceEnumeration.hasMoreElements()) {
            networkInterface = networkInterfaceEnumeration.nextElement();
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            InetAddress inetAddress = null;
            while (inetAddressEnumeration.hasMoreElements()) {
                inetAddress = inetAddressEnumeration.nextElement();
                if (inetAddress instanceof Inet6Address && ip.equals(inetAddress.getHostAddress())) {
                    return (Inet6Address) inetAddress;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.err.println(getLocalHost());
        System.err.println(getLocalHost().toString());
        System.err.println(getLocalHost().toString().split("/")[1]);
        System.err.println(getInet4Address("192.168.31.29"));
    }
}
