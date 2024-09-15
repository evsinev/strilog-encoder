package com.payneteasy.strilog.encoder.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Hostnames {

    public static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return "unknown";
        }
    }
}
