package com.xiongyingqi.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author qi
 * @version 2018/9/17
 */
public class DnsDemo {
    public static void main(String[] args) throws UnknownHostException {
        String host = "baidu.com";
        InetAddress[] allByName = InetAddress.getAllByName(host);
        System.out.println(Arrays.toString(allByName));
        for (InetAddress inetAddress : allByName) {
            System.out.println(inetAddress.getHostAddress());
            InetAddress[] allByName1 = InetAddress.getAllByName(inetAddress.getHostAddress());
            System.out.println(Arrays.toString(allByName1));

        }
    }
}
