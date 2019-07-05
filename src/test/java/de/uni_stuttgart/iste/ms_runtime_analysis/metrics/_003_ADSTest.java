package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.HashMap;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class _003_ADSTest {

    @Test
    public void calculateMetric() {

        DefaultDirectedGraph<Service, DefaultEdge> graph =
                new DefaultDirectedGraph<>(DefaultEdge.class);

        Service a = new Service("a");
        Service b = new Service("b");
        Service c = new Service("c");
        Service d = new Service("d");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(a, b);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        graph.addEdge(c, a);
        graph.addEdge(d, c);

        _003_ADS metricInterface = new _003_ADS();
        Metric result = metricInterface.calculateMetric(graph);

        HashMap<String, Double> expected = new HashMap<>();
        expected.put("a", 1.0);
        expected.put("b", 1.0);
        expected.put("c", 2.0);
        expected.put("d", 1.0);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_MULTIPLE_RESULTS, (int) result.getResultStatus());
        assertEquals(expected, result.getMultipleResults());
        assertNull(result.getResultDetails());

    }

}
