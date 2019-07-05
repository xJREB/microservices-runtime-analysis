package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.HashMap;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric WSIC
 * Description: Weighted number of exposed interfaces or operations per service (all weights = 1.0)
 * Source: M. Hirzalla, J. Cleland-Huang and A. Arsanjani, A Metrics Suite for Evaluating Flexibility and Complexity in Service Oriented Architectures, 2009, In Service-Oriented Computing – ICSOC 2008 Workshops: ICSOC 2008 International Workshops, Sydney, Australia, December 1st, 2008, Revised Selected Papers, Bernd J. Krämer, Kwei-Jay Lin, and Priya Narasimhan (Eds.). Lecture Notes in Computer Science, Vol. 4749. Springer Berlin Heidelberg, Berlin, Heidelberg, 41–52. DOI: https://doi.org/10.1007/978-3-642-01247-1_5
 */
public class _009_WSIC implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Weighted Service Interface Count", "WSIC",
                "Weighted number of exposed interfaces or operations per service (all weights = 1.0)",
                "Value range: [0,INF), lower is better", Metric.RESULT_TYPE_DOUBLE);
        HashMap<String, Double> results = new HashMap<>();

        for (Service service : graph.vertexSet()) {
            results.put(service.getName(), (double) service.getOperations().length);
        }

        metric.setMultipleResults(results);
        metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        return metric;
    }

}
