package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines helper functions for date processing
 */
public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converts a date string of format yyyy/MM/dd HH:mm:ss into an unix timestamp
     * 
     * @param date date string to be converted
     * @return created timestamp
     * @throws ParseException date string is not correctly formatted
     */
    public static long dateToTimestamp(String date) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Timestamp ts = new Timestamp((dateFormat.parse(date)).getTime());
        return ts.getTime();

    }

    /**
     * Converts a unix timestamp into a date string of format yyyy/MM/dd HH:mm:ss
     * 
     * @param timestamp timestamp to be converted
     * @return created date string
     */
    public static String timestampToDate(long timestamp) {

        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);

    }

}
