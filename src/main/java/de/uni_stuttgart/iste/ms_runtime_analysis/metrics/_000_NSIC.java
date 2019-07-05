package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.HashMap;
import java.util.HashSet;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.GraphUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric NSIC
 * Description: Number of services involved in the compound service
 * Source: D. Rud, A. Schmietendorf and R. R. Dumke, Product Metrics for Service-Oriented Infrastructures, 2006, In IWSM/MetriKon.
 */
public class _000_NSIC implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        int valueRangeEnd = graph.vertexSet().size();
        Metric metric = new Metric("Number of Services Involved in the Compound Service", "NSIC",
                "Number of services involved in the compound service",
                "Value range: [0," + valueRangeEnd + "], lower is better", Metric.RESULT_TYPE_INT);
        HashMap<String, Double> results = new HashMap<>();

        for (Service service : graph.vertexSet()) {
            HashSet<Service> dependencies = new HashSet<>();
            results.put(service.getName(),
                    (double) GraphUtils.getDependencies(graph, service, dependencies).size());
        }

        metric.setMultipleResults(results);
        metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        return metric;
    }

}
