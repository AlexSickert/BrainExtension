/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Log.Log;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static server.Main.acceptedAddress;

/**
 *
 * @author xandi
 */
public class CheckUrl {
    
   

//    public static void checkUrl() {
//        try {
//            InetAddress inetAddress = InetAddress.getLocalHost();
//
//            inetAddress = InetAddress.getByName("www.ssl-id1.de");
//            displayStuff("www.ssl-id1.de", inetAddress);
//
//            InetAddress[] inetAddressArray = InetAddress.getAllByName("www.ssl-id1.de");
//            for (int i = 0; i < inetAddressArray.length; i++) {
//                displayStuff("www.ssl-id1.de #" + (i + 1), inetAddressArray[i]);
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }

//    private static void displayStuff(String whichHost, InetAddress inetAddress) {
//        System.out.println("--------------------------");
//        System.out.println("Which Host:" + whichHost);
//        System.out.println("Canonical Host Name:" + inetAddress.getCanonicalHostName());
//        System.out.println("Host Name:" + inetAddress.getHostName());
//        System.out.println("Host Address:" + inetAddress.getHostAddress());
//    }

    public static boolean checkAddress(HttpExchange he) {
        String x = he.getRemoteAddress().toString();

//        if (x.contains(acceptedAddress)) {
//            return true;
//        } else {
//
//            Log.log("error", "acceptedAddress is " + acceptedAddress + "but address is " + x);
//
//            return false;
//        }

        return true;

    }

}
