package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class _022_CSDTest {

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
        graph.addEdge(c, a);
        graph.addEdge(a, c);
        graph.addEdge(c, b);
        graph.addEdge(b, a);
        graph.addEdge(a, d);
        graph.addEdge(d, a);
        graph.addEdge(b, d);
        graph.addEdge(d, b);

        _022_CSD metricInterface = new _022_CSD();
        Metric result = metricInterface.calculateMetric(graph);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_SINGLE_RESULT, (int) result.getResultStatus());
        assertEquals(6.0, result.getSingleResult(), 0.0);
        assertArrayEquals(new String[] {"a --> b --> c --> a", "a --> b --> d --> a",
                "a --> c --> b --> a", "a --> c --> b --> d --> a", "a --> d --> b --> c --> a",
                "a --> d --> b --> a"}, result.getResultDetails());

    }

}
