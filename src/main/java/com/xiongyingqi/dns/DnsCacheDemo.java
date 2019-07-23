package com.xiongyingqi.dns;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author xiongyingqi
 * @since 20171009//
 */
public class DnsCacheDemo {
    public static void main(String[] args) throws InterruptedException {
        // run this java with jvm args:
        // -Dnetworkaddress.cache.ttl=10
        // throws: java.net.UnknownHostException: UnknownHost
        // run after 10 seconds...
        // then:
        // echo "127.0.0.1 UnknownHost" >> /etc/hosts
        // maybe happens:  Connection refused (dns cache cleared)

        String urlStr = "http://UnknownHost";
        if (args.length > 0) {
            System.err.println("No parameter input! so use domain: UnknownHost");
            urlStr = args[0];
        }
        boolean end = false;
        while (!end) {
            try {
                URL url = new URL(urlStr);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(1000);
                urlConnection.connect();
                Object content = urlConnection.getContent();
                System.out.println(content);
                end = true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }

    }
}
