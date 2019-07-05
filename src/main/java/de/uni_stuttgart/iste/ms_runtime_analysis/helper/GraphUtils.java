package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Defines helper functions for graph processing
 */
public class GraphUtils {

    private GraphUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Calculates the maximum number of cycles in a complete graph
     * 
     * @param size number of vertices in the complete graph
     * @return maximum number of cycles
     */
    public static long maxCyclesInCompleteGraph(int size) {

        if (size < 3) {
            return 0;
        }

        long sum = 0;

        for (int k = 3; k <= size; k++) {

            sum += (MathUtils.factorial(size)
                    / (MathUtils.factorial(k) * MathUtils.factorial((long) size - k)))
                    * MathUtils.factorial((long) k - 1);

        }

        return sum;
    }

    /**
     * Find all cycles in a directed graph
     * 
     * @param graph graph to be searched for cycles
     * @return array of cycles found in the graph
     */
    public static String[] getCycles(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        ArrayList<String> cycles = new ArrayList<>();

        TarjanSimpleCycles<Service, DefaultEdge> cycleDetector = new TarjanSimpleCycles<>(graph);

        for (List<Service> currentCycle : cycleDetector.findSimpleCycles()) {

            // Remove too small cycles
            if (currentCycle.size() < 3) {
                continue;
            }

            StringBuilder thisCycle = new StringBuilder();

            for (Service currentCycleElement : currentCycle) {
                thisCycle.append(currentCycleElement.getName()).append(" --> ");
            }


            thisCycle.append(currentCycle.get(0).getName());

            cycles.add(thisCycle.toString());
        }

        return cycles.toArray(new String[0]);
    }

    /**
     * Finds vertex and all children of this vertex
     * 
     * @param graph        graph to be searched
     * @param service      vertex to start at
     * @param dependencies already found children
     * @return vertex and list of all children
     */
    public static Set<Service> getDependencies(DefaultDirectedGraph<Service, DefaultEdge> graph,
            Service service, Set<Service> dependencies) {

        dependencies.add(service);

        for (DefaultEdge edge : graph.outgoingEdgesOf(service)) {

            if (!dependencies.contains(graph.getEdgeTarget(edge))) {
                dependencies
                        .addAll(getDependencies(graph, graph.getEdgeTarget(edge), dependencies));
            }

        }

        return dependencies;
    }

}
