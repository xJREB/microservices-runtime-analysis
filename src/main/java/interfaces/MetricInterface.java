package interfaces;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import models.Metric;
import models.Service;

/**
 * Interface to calculate a metric from the runtime data in the canonical format
 */
public interface MetricInterface {

    /**
     * Calculates a specific metric from the provided runtime data
     * 
     * @param graph runtime data to be used
     * @return calculated metric
     */
    Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph);

}
