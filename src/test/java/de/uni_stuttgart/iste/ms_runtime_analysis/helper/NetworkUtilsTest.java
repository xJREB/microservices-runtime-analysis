package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class NetworkUtilsTest {

    @Test
    public void fetchDocument() {

        // No url
        try {
            NetworkUtils.fetchDocument(null);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }

        // Unsupported protocol
        try {
            String result = NetworkUtils.fetchDocument("ftp://example.com/");
            assertNull(result);
        } catch (Exception e) {
            fail();
        }

    }
}
