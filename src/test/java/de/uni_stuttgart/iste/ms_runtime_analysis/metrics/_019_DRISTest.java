package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class _019_DRISTest {

    @Test
    public void calculateMetric() {

        DefaultDirectedGraph<Service, DefaultEdge> graph =
                new DefaultDirectedGraph<>(DefaultEdge.class);

        Service a = new Service("a");
        Service b = new Service("b");
        Service c = new Service("c");
        Service d = new Service("d");

        a.addOperation(new Operation("test-parameter-1"));
        b.addOperation(new Operation("test-parameter-2"));
        b.addOperation(new Operation("test-parameter-3"));
        b.addOperation(new Operation("test-parameter-4"));
        c.addOperation(new Operation("test-parameter-5"));
        d.addOperation(new Operation("test-parameter-6"));
        d.addOperation(new Operation("test-parameter-7"));

        a.findOperation("test-parameter-1").increaseCallCount(d);
        b.findOperation("test-parameter-2").increaseCallCount(c);
        b.findOperation("test-parameter-2").increaseCallCount(c);
        b.findOperation("test-parameter-2").increaseCallCount(c);
        b.findOperation("test-parameter-2").increaseCallCount(c);
        b.findOperation("test-parameter-2").increaseCallCount(a);
        c.findOperation("test-parameter-5").increaseCallCount(a);
        c.findOperation("test-parameter-5").increaseCallCount(a);
        d.findOperation("test-parameter-6").increaseCallCount(c);
        d.findOperation("test-parameter-6").increaseCallCount(b);

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        graph.addEdge(d, c);

        _019_DRIS metricInterface = new _019_DRIS();
        Metric result = metricInterface.calculateMetric(graph);

        HashMap<String, Map<String, Double>> expected = new HashMap<>();

        HashMap<String, Double> subExpected = new HashMap<>();
        subExpected.put("d", 1.0);
        expected.put("a", subExpected);

        subExpected = new HashMap<>();
        subExpected.put("a", 0.2);
        subExpected.put("c", 0.8);
        expected.put("b", subExpected);

        subExpected = new HashMap<>();
        subExpected.put("a", 1.0);
        expected.put("c", subExpected);

        subExpected = new HashMap<>();
        subExpected.put("b", 0.5);
        subExpected.put("c", 0.5);
        expected.put("d", subExpected);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_NESTED_RESULTS, (int) result.getResultStatus());
        assertEquals(expected, result.getNestedResults());
        assertNull(result.getResultDetails());

    }

}
