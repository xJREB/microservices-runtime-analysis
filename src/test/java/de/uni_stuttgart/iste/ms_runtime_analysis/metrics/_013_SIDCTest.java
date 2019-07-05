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

public class _013_SIDCTest {

    @Test
    public void calculateMetric() {

        DefaultDirectedGraph<Service, DefaultEdge> graph =
                new DefaultDirectedGraph<>(DefaultEdge.class);

        Service a = new Service("a");
        Service b = new Service("b");
        Service c = new Service("c");
        Service d = new Service("d");

        a.addOperation(new Operation("test-parameter-1:test"));
        a.addOperation(new Operation("test-parameter-1:test"));
        b.addOperation(new Operation("test-parameter-2:test"));
        b.addOperation(new Operation("test-parameter-2:test"));
        b.addOperation(new Operation("test-parameter-3:test"));
        c.addOperation(new Operation("test-parameter-4:test"));
        d.addOperation(new Operation("test-parameter-5:test"));
        d.addOperation(new Operation("test-parameter-6:test"));

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        graph.addEdge(d, c);

        _013_SIDC metricInterface = new _013_SIDC();
        Metric result = metricInterface.calculateMetric(graph);

        HashMap<String, Double> expected = new HashMap<>();
        expected.put("a", 1.0);
        expected.put("b", 0.67);
        expected.put("c", 1.0);
        expected.put("d", 0.0);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_MULTIPLE_RESULTS, (int) result.getResultStatus());
        assertEquals(expected, result.getMultipleResults());
        assertNull(result.getResultDetails());

    }

}