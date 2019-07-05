package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;

public class ArgumentParserTest {

        @Test
        public void parse() {

                TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

                // Check debugging parameter
                ArgumentParser parser = new ArgumentParser(new String[] {"--debug", "--start_date",
                                "2018/01/01 01:02:03", "--end_date", "2018/10/15 22:14:38",
                                "--integrators", "test", "--exporters", "test"});
                parser.parse();

                assertTrue(Logger.debugEnabled());

                parser = new ArgumentParser(new String[] {"--start_date", "2018/01/01 01:02:03",
                                "--end_date", "2018/10/15 22:14:38", "--integrators", "test",
                                "--exporters", "test"});
                parser.parse();

                assertFalse(Logger.debugEnabled());

                // Check timestamp date parameters
                parser = new ArgumentParser(new String[] {"--start_date", "1514763923000",
                                "--end_date", "1539635578000", "--integrators", "test1,test2",
                                "--exporters", "test3,test4,test5", "--integrator_params",
                                "zipkin:url=https://example.com/", "zipkin:test1=test2",
                                "tcpdump:test3=test4", "--exporter_params", "xml:filename=test.xml",
                                "xml:filename2=test2.xml", "json:filename=test.json"});
                Configuration configuration = parser.parse();

                assertEquals(1514763923000L, configuration.getStartDate());
                assertEquals(1539635578000L, configuration.getEndDate());

                // Check normal date / integrator / exporter parameters
                parser = new ArgumentParser(new String[] {"--start_date", "2018/01/01 01:02:03",
                                "--end_date", "2018/10/15 22:14:38", "--integrators", "test1,test2",
                                "--exporters", "test3,test4,test5", "--integrator_params",
                                "zipkin:url=https://example.com/", "zipkin:test1=test2",
                                "tcpdump:test3=test4", "--exporter_params", "xml:filename=test.xml",
                                "xml:filename2=test2.xml", "json:filename=test.json"});
                configuration = parser.parse();

                assertEquals(1514768523000L, configuration.getStartDate());
                assertEquals(1539641678000L, configuration.getEndDate());
                assertArrayEquals(new String[] {"test1", "test2"}, configuration.getIntegrators());
                assertArrayEquals(new String[] {"test3", "test4", "test5"},
                                configuration.getExporters());

                Map<String, String> expected = new HashMap<>();
                expected.put("url", "https://example.com/");
                expected.put("test1", "test2");

                assertEquals(expected, configuration.getIntegratorParams("zipkin"));

                expected = new HashMap<>();
                expected.put("test3", "test4");

                assertEquals(expected, configuration.getIntegratorParams("tcpdump"));

                expected = new HashMap<>();
                expected.put("filename", "test.xml");
                expected.put("filename2", "test2.xml");

                assertEquals(expected, configuration.getExporterParams("xml"));

                expected = new HashMap<>();
                expected.put("filename", "test.json");

                assertEquals(expected, configuration.getExporterParams("json"));

        }
}
