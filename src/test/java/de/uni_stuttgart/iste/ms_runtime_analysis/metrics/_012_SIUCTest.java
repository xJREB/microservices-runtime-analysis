package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.HashMap;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class _012_SIUCTest {

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
        c.findOperation("test-parameter-5").increaseCallCount(a);
        c.findOperation("test-parameter-5").increaseCallCount(a);
        d.findOperation("test-parameter-6").increaseCallCount(c);

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        graph.addEdge(d, c);

        _012_SIUC metricInterface = new _012_SIUC();
        Metric result = metricInterface.calculateMetric(graph);

        HashMap<String, Double> expected = new HashMap<>();
        expected.put("a", 1.0);
        expected.put("b", 0.33);
        expected.put("c", 1.0);
        expected.put("d", 0.5);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_MULTIPLE_RESULTS, (int) result.getResultStatus());
        assertEquals(expected, result.getMultipleResults());
        assertNull(result.getResultDetails());

    }

}
