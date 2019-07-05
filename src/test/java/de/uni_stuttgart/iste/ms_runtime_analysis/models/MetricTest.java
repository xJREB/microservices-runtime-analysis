package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class MetricTest {

    @Test
    public void nameTests() {

        Metric metric = new Metric("", "", "", "", 0);

        assertEquals("", metric.getLongName());
        metric.setLongName("test-metric");
        assertEquals("test-metric", metric.getLongName());

    }

    @Test
    public void abbreviationTests() {

        Metric metric = new Metric("", "", "", "", 0);

        assertEquals("", metric.getAbbreviation());
        metric.setAbbreviation("tm");
        assertEquals("tm", metric.getAbbreviation());

    }

    @Test
    public void descriptionTests() {

        Metric metric = new Metric("", "", "", "", 0);

        assertEquals("", metric.getDescription());
        metric.setDescription("test description");
        assertEquals("test description", metric.getDescription());

    }

    @Test
    public void interpretationTests() {

        Metric metric = new Metric("", "", "", "", 0);

        assertEquals("", metric.getInterpretation());
        metric.setInterpretation("test interpretation");
        assertEquals("test interpretation", metric.getInterpretation());

    }

    @Test
    public void resultStatusTests() {

        Metric metric = new Metric("", "", "", "", 0);

        assertEquals(Metric.RESULT_NOT_PROCESSED_YET, (int) metric.getResultStatus());
        metric.setResultStatus(Metric.RESULT_ERROR);
        assertEquals(Metric.RESULT_ERROR, (int) metric.getResultStatus());

    }

    @Test
    public void resultTypeTests() {

        Metric metric = new Metric("", "", "", "", Metric.RESULT_TYPE_INT);

        assertEquals(Metric.RESULT_TYPE_INT, metric.getResultType());
        metric.setResultType(Metric.RESULT_TYPE_PERCENT);
        assertEquals(Metric.RESULT_TYPE_PERCENT, metric.getResultType());

    }

    @Test
    public void resultStatusMessageTests() {
        Metric metric = new Metric("", "", "", "", 0);

        assertNull(metric.getResultStatusMessage());
        metric.setResultStatusMessage("test message");
        assertEquals("test message", metric.getResultStatusMessage());

    }

    @Test
    public void singleResultTests() {

        Metric metric = new Metric("", "", "", "", 0);

        metric.setSingleResult(1.23);
        assertEquals(1.23, metric.getSingleResult(), 0.0);

    }

    @Test
    public void multipleResultsTests() {

        Metric metric = new Metric("", "", "", "", 0);

        HashMap<String, Double> results = new HashMap<>();
        results.put("result-1", 1.23);
        results.put("result-2", 4.56);

        metric.setMultipleResults(results);

        assertEquals(1.23, metric.getMultipleResults().get("result-1"), 0.0);
        assertEquals(4.56, metric.getMultipleResults().get("result-2"), 0.0);

    }

    @Test
    public void nestedResultsTests() {

        Metric metric = new Metric("", "", "", "", 0);

        HashMap<String, Map<String, Double>> nestedResults = new HashMap<>();

        HashMap<String, Double> subResults = new HashMap<>();

        subResults.put("sub-result-1", 1.23);
        subResults.put("sub-result-2", 4.56);

        nestedResults.put("result-1", subResults);

        subResults = new HashMap<>();

        subResults.put("sub-result-3", 3.21);
        subResults.put("sub-result-4", 6.54);

        nestedResults.put("result-2", subResults);

        metric.setNestedResults(nestedResults);

        assertEquals(1.23, metric.getNestedResults().get("result-1").get("sub-result-1"), 0.0);
        assertEquals(4.56, metric.getNestedResults().get("result-1").get("sub-result-2"), 0.0);
        assertNull(metric.getNestedResults().get("result-1").get("sub-result-3"));
        assertEquals(3.21, metric.getNestedResults().get("result-2").get("sub-result-3"), 0.0);
        assertEquals(6.54, metric.getNestedResults().get("result-2").get("sub-result-4"), 0.0);
        assertNull(metric.getNestedResults().get("result-3"));

    }

    @Test
    public void resultDetailsTests() {

        Metric metric = new Metric("", "", "", "", 0);

        assertNull(metric.getResultDetails());
        metric.setResultDetails(new String[] {"detail-1", "detail-2"});

        assertEquals(2, metric.getResultDetails().length);
        assertEquals("detail-1", metric.getResultDetails()[0]);
        assertEquals("detail-2", metric.getResultDetails()[1]);

    }

}
