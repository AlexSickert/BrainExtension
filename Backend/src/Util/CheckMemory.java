/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Log.Log;

/**
 * 
 * @author alex
 */
public class CheckMemory {

    public static void dumpStatus() {
        
        long tmpVal;

        /* Total number of processors or cores available to the JVM */
        
        Log.log("info", "Total number of processors or cores available to the JVM (cores): "
                + Runtime.getRuntime().availableProcessors());

        /* Total amount of free memory available to the JVM */
        tmpVal = (long) Runtime.getRuntime().freeMemory() / (1024 * 1024);
        Log.log("info", "Total amount of free memory available to the JVM (Megabytes): "
                + tmpVal);

        /* This will return Long.MAX_VALUE if there is no preset limit */
        tmpVal = (long) Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long maxMemory = Runtime.getRuntime().maxMemory();
        
        /* Maximum amount of memory the JVM will attempt to use */
        Log.log("info", "Maximum memory (Megabytes / bytes): "
                + (maxMemory == Long.MAX_VALUE ? "no limit" : tmpVal));

        /* Total memory currently in use by the JVM */
        tmpVal = (long) Runtime.getRuntime().totalMemory() / (1024 * 1024);
        Log.log("info", "Total memory currently in use by the JVM (Megabytes): "
                + tmpVal);

    }

}
