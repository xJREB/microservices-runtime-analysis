package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LoggerTest {

    @Test
    public void setDebugEnabled() {

        Logger.setDebugEnabled(true);
        assertTrue(Logger.debugEnabled());
        Logger.setDebugEnabled(false);
        assertFalse(Logger.debugEnabled());

    }

    @Test
    public void debugEnabled() {

        Logger.setDebugEnabled(true);
        assertTrue(Logger.debugEnabled());
        Logger.setDebugEnabled(false);
        assertFalse(Logger.debugEnabled());

    }

}
