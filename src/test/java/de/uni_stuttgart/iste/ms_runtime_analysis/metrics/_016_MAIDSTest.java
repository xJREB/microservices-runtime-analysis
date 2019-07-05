package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class _016_MAIDSTest {

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

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        graph.addEdge(d, c);

        _016_MAIDS metricInterface = new _016_MAIDS();
        Metric result = metricInterface.calculateMetric(graph);

        assertNotNull(result);
        assertEquals(Metric.RESULT_OK_SINGLE_RESULT, (int) result.getResultStatus());
        assertEquals(1.25, result.getSingleResult(), 0.0);
        assertNull(result.getResultDetails());

    }

}
