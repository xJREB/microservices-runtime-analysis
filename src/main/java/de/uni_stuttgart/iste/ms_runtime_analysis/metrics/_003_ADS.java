package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.HashMap;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric ADS
 * Description: Number of other services a service depends on
 * Source: D. Rud, A. Schmietendorf and R. R. Dumke, Product Metrics for Service-Oriented Infrastructures, 2006, In IWSM/MetriKon.
 */
public class _003_ADS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        int numberOfServices = graph.vertexSet().size();
        int valueRangeEnd = (numberOfServices - 1) < 0 ? 0 : (numberOfServices - 1);
        Metric metric = new Metric("Absolute Dependence of the Service", "ADS",
                "Number of other services a service depends on",
                "Value range: [0," + valueRangeEnd + "], lower is better", Metric.RESULT_TYPE_INT);
        HashMap<String, Double> results = new HashMap<>();

        for (Service service : graph.vertexSet()) {
            results.put(service.getName(), (double) graph.outDegreeOf(service));
        }

        metric.setMultipleResults(results);
        metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        return metric;
    }

}
