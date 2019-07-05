package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.util.TimeZone;
import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void dateToTimestamp() {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // Correctly formatted date
        try {
            long result = DateUtils.dateToTimestamp("2018/02/02 10:15:20");
            assertEquals(1517566520000L, result);
        } catch (ParseException e) {
            fail();
        }

        // Not fully correctly formatted date, but still okay
        try {
            long result = DateUtils.dateToTimestamp("2018/4/2 4:5:3");
            assertEquals(1522641903000L, result);
        } catch (ParseException e) {
            fail();
        }

        // Date missing
        try {
            DateUtils.dateToTimestamp("08:04:42");
            fail();
        } catch (ParseException e) {
            assertTrue(true);
        }

        // Time missing
        try {
            DateUtils.dateToTimestamp("2018/02/02");
            fail();
        } catch (ParseException e) {
            assertTrue(true);
        }

        // Date string missing
        try {
            DateUtils.dateToTimestamp(null);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void timestampToDate() {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // Positive timestamp
        String result = DateUtils.timestampToDate(1517562920000L);
        assertEquals("2018-02-02 09:15:20", result);

        // Negative timestamp
        result = DateUtils.timestampToDate(-10000000L);
        assertEquals("1969-12-31 21:13:20", result);

    }
}
