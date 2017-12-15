package com.libsimsync.managing;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Nickitakalinkin on 16.12.17.
 */
public class LocalNetworkInformation {

    public static void getNetworkinformation() {

        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();

            for (NetworkInterface neit : Collections.list(networkInterfaces)) {
                displayInformation(neit);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static void displayInformation(NetworkInterface networkInterface) {

      System.out.println("Display Name: " + networkInterface.getDisplayName());
      System.out.println("Name: " + networkInterface.getName());

        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        for(InetAddress iAdr : Collections.list(inetAddresses)) {
            System.out.println(iAdr.getHostAddress());
            System.out.println("Address: " + iAdr);
            System.out.println("_____________");
        }
        System.out.println("\n");
    }

}
