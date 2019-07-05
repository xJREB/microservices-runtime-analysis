package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines helper functions for logging
 */
public class Logger {

    private static boolean debugEnabled = false;

    private Logger() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the current date formatted as yyyy/MM/dd HH:mm:ss
     * 
     * @return current date
     */
    private static String getDate() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    /**
     * Write a string to stdout
     * 
     * @param line string to be written
     */
    public static void out(String line) {
        System.out.println(getDate() + ": " + line);
    }

    /**
     * Write a string to stderr
     * 
     * @param line string to be written
     */
    public static void err(String line) {
        System.err.println(getDate() + ": " + line);
    }

    /**
     * Write a string to stdout if debugging is enabled
     * 
     * @param line string to be written
     */
    public static void debug(String line) {
        if (debugEnabled) {
            out(line);
        }
    }

    /**
     * Enable/Disable debugging mode
     * 
     * @param enabled new state of debugging mode
     */
    public static void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }

    /**
     * Check if debugging is enabled
     * 
     * @return state of debugging mode
     */
    public static boolean debugEnabled() {
        return debugEnabled;
    }

}
