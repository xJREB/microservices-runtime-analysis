package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

public class GraphUtilsTest {

    @Test
    public void maxCyclesInCompleteGraph() {

        long result = GraphUtils.maxCyclesInCompleteGraph(-10);
        assertEquals(0, result);

        result = GraphUtils.maxCyclesInCompleteGraph(-1);
        assertEquals(0, result);

        result = GraphUtils.maxCyclesInCompleteGraph(0);
        assertEquals(0, result);

        result = GraphUtils.maxCyclesInCompleteGraph(1);
        assertEquals(0, result);

        result = GraphUtils.maxCyclesInCompleteGraph(2);
        assertEquals(0, result);

        result = GraphUtils.maxCyclesInCompleteGraph(3);
        assertEquals(2, result);

        result = GraphUtils.maxCyclesInCompleteGraph(4);
        assertEquals(14, result);

        result = GraphUtils.maxCyclesInCompleteGraph(5);
        assertEquals(74, result);

        result = GraphUtils.maxCyclesInCompleteGraph(10);
        assertEquals(1112028, result);

    }

    @Test
    public void getCycles() {

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

        // Check zero cycles
        String[] result = GraphUtils.getCycles(graph);
        assertArrayEquals(new String[] {}, result);

        // Check one cycle
        graph.addEdge(a, b);
        graph.addEdge(b, c);
        graph.addEdge(c, a);

        result = GraphUtils.getCycles(graph);
        assertArrayEquals(new String[] {"a --> b --> c --> a"}, result);

        // Check one cycle in two directions
        graph.addEdge(a, c);
        graph.addEdge(c, b);
        graph.addEdge(b, a);

        result = GraphUtils.getCycles(graph);
        assertArrayEquals(new String[] {"a --> b --> c --> a", "a --> c --> b --> a"}, result);

        // Check many cycles
        graph.addEdge(a, d);
        graph.addEdge(d, a);
        graph.addEdge(b, d);
        graph.addEdge(d, b);

        result = GraphUtils.getCycles(graph);
        assertArrayEquals(new String[] {"a --> b --> c --> a", "a --> b --> d --> a",
                "a --> c --> b --> a", "a --> c --> b --> d --> a", "a --> d --> b --> c --> a",
                "a --> d --> b --> a"}, result);

    }

    @Test
    public void getDependencies() {

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

        // Check zero dependencies
        Set<Service> result = GraphUtils.getDependencies(graph, a, new HashSet<>());
        Set<Service> expected = new HashSet<>();

        expected.add(a);

        assertEquals(expected, result);

        // Check one dependency
        graph.addEdge(a, b);

        result = GraphUtils.getDependencies(graph, a, new HashSet<>());
        expected.add(b);

        assertEquals(expected, result);

        // Check multi level dependencies without cycles
        graph.addEdge(b, c);
        graph.addEdge(c, d);

        result = GraphUtils.getDependencies(graph, a, new HashSet<>());
        expected.add(c);
        expected.add(d);

        assertEquals(expected, result);

        // Check multi level dependencies with cycles
        graph.addEdge(b, d);
        graph.addEdge(d, b);

        result = GraphUtils.getDependencies(graph, a, new HashSet<>());

        assertEquals(expected, result);

    }
}
