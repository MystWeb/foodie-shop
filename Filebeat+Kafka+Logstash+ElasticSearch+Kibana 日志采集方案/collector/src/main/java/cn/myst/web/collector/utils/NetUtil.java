package cn.myst.web.collector.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * $NetUtil
 *
 * @author fengxuechao
 * @since 2019年1月15日 下午4:59:02
 */
public class NetUtil {

    private static final Pattern PATTERN = Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");

    public static String normalizeAddress(String address) {
        String[] blocks = address.split("[:]");
        if (blocks.length > 2) {
            throw new IllegalArgumentException(address + " is invalid");
        }
        String host = blocks[0];
        int port = 80;
        if (blocks.length > 1) {
            port = Integer.valueOf(blocks[1]);
        } else {
            address += ":" + port; //use default 80
        }
        String serverAddr = String.format("%s:%d", host, port);
        return serverAddr;
    }

    public static String getLocalAddress(String address) {
        String[] blocks = address.split("[:]");
        if (blocks.length != 2) {
            throw new IllegalArgumentException(address + " is invalid address");
        }
        String host = blocks[0];
        int port = Integer.valueOf(blocks[1]);

        if ("0.0.0.0".equals(host)) {
            return String.format("%s:%d", NetUtil.getLocalIp(), port);
        }
        return address;
    }

    private static int matchedIndex(String ip, String[] prefix) {
        for (int i = 0; i < prefix.length; i++) {
            String p = prefix[i];
            if ("*".equals(p)) { //*, assumed to be IP
                if (ip.startsWith("127.") ||
                        ip.startsWith("10.") ||
                        ip.startsWith("172.") ||
                        ip.startsWith("192.")) {
                    continue;
                }
                return i;
            } else {
                if (ip.startsWith(p)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static String getLocalIp(String ipPreference) {
        if (ipPreference == null) {
            ipPreference = "*>10>172>192>127";
        }
        String[] prefix = ipPreference.split("[> ]+");
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            String matchedIp = null;
            int matchedIdx = -1;
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> en = ni.getInetAddresses();
                while (en.hasMoreElements()) {
                    InetAddress addr = en.nextElement();
                    String ip = addr.getHostAddress();
                    Matcher matcher = PATTERN.matcher(ip);
                    if (matcher.matches()) {
                        int idx = matchedIndex(ip, prefix);
                        if (idx == -1) {
                            continue;
                        }
                        if (matchedIdx == -1) {
                            matchedIdx = idx;
                            matchedIp = ip;
                        } else {
                            if (matchedIdx > idx) {
                                matchedIdx = idx;
                                matchedIp = ip;
                            }
                        }
                    }
                }
            }
            if (matchedIp != null) {
                return matchedIp;
            }
            return "127.0.0.1";
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }

    public static String getLocalIp() {
        return getLocalIp("*>10>172>192>127");
    }

    public static String remoteAddress(SocketChannel channel) {
        SocketAddress addr = channel.socket().getRemoteSocketAddress();
        String res = String.format("%s", addr);
        return res;
    }

    public static String localAddress(SocketChannel channel) {
        SocketAddress addr = channel.socket().getLocalSocketAddress();
        String res = String.format("%s", addr);
        return addr == null ? res : res.substring(1);
    }

    public static String getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        int index = name.indexOf("@");
        if (index != -1) {
            return name.substring(0, index);
        }
        return null;
    }

    public static String getLocalHostName() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }
}