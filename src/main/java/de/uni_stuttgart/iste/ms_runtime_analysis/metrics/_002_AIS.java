package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.HashMap;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric AIS
 * Description: Number of services which depend on a service
 * Source: D. Rud, A. Schmietendorf and R. R. Dumke, Product Metrics for Service-Oriented Infrastructures, 2006, In IWSM/MetriKon.
 */
public class _002_AIS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        int numberOfServices = graph.vertexSet().size();
        int valueRangeEnd = (numberOfServices - 1) < 0 ? 0 : (numberOfServices - 1);
        Metric metric = new Metric("Absolute Importance of the Service", "AIS",
                "Number of services which depend on a service",
                "Value range: [0," + valueRangeEnd + "], lower is better", Metric.RESULT_TYPE_INT);
        HashMap<String, Double> results = new HashMap<>();

        for (Service service : graph.vertexSet()) {
            results.put(service.getName(), (double) graph.inDegreeOf(service));
        }

        metric.setMultipleResults(results);
        metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        return metric;
    }

}
