package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class _014_RFOTest {

    @Test
    public void calculateMetric() {

        DefaultDirectedGraph<Service, DefaultEdge> graph =
                new DefaultDirectedGraph<>(DefaultEdge.class);

        Service a = new Service("a");
        Service b = new Service("b");
        Service c = new Service("c");
        Service d = new Service("d");

        a.addOperation(new Operation("test-parameter-1:test"));
        b.addOperation(new Operation("test-parameter-2:test"));
        b.addOperation(new Operation("test-parameter-3:test"));
        c.addOperation(new Operation("test-parameter-4:test"));
        d.addOperation(new Operation("test-parameter-6:test"));

        b.findOperation("test-parameter-2:test").addResponseForOperation("rfo-1");
        b.findOperation("test-parameter-2:test").addResponseForOperation("rfo-2");
        b.findOperation("test-parameter-2:test").addResponseForOperation("rfo-3");
        d.findOperation("test-parameter-6:test").addResponseForOperation("rfo-4");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        graph.addEdge(d, c);

        _014_RFO metricInterface = new _014_RFO();
        Metric result = metricInterface.calculateMetric(graph);

        HashMap<String, Map<String, Double>> expected = new HashMap<>();

        HashMap<String, Double> subExpected = new HashMap<>();
        subExpected.put("[test-parameter-1:test]", 0.0);
        expected.put("a", subExpected);

        subExpected = new HashMap<>();
        subExpected.put("[test-parameter-3:test]", 0.0);
        subExpected.put("[test-parameter-2:test]", 3.0);
        expected.put("b", subExpected);

        subExpected = new HashMap<>();
        subExpected.put("[test-parameter-4:test]", 0.0);
        expected.put("c", subExpected);

        subExpected = new HashMap<>();
        subExpected.put("[test-parameter-6:test]", 1.0);
        expected.put("d", subExpected);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_NESTED_RESULTS, (int) result.getResultStatus());
        assertEquals(expected, result.getNestedResults());
        assertArrayEquals(new String[] {"b ([test-parameter-2:test]): [rfo-1, rfo-2, rfo-3]",
                "d ([test-parameter-6:test]): [rfo-4]"}, result.getResultDetails());

    }

}
