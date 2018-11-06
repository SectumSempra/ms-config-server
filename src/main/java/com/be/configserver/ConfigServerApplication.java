package com.be.configserver;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static Map<String, Object> getMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        String ip = getIpAdressBy10();
        map.put("ip", ip);
        return map;

    }

    private static String getIpAdressBy10() throws SocketException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.getHostAddress().startsWith("10."))
                    return i.getHostAddress();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(ConfigServerApplication.class);
        application.setDefaultProperties(getMap());
        application.run(args);

    }
}
