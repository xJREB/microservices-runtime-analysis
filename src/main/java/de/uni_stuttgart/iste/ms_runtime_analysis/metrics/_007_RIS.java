package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.HashMap;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.MathUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric RIS
 * Description: Degree to which the other services depend on a service
 * Source: Z. Qingqing and L. Xinke, Complexity Metrics for Service-Oriented Systems, 2009, In 2009 Second International Symposium on Knowledge Acquisition and Modeling, Vol. 3. IEEE, 375–378. DOI: https://doi.org/10.1109/KAM.2009.90
 */
public class _007_RIS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Relative Importance of Service", "RIS",
                "Degree to which the other services depend on a service",
                "Value range: [0,1), lower is better", Metric.RESULT_TYPE_PERCENT);
        HashMap<String, Double> results = new HashMap<>();

        if (graph.vertexSet().isEmpty()) {
            metric.setResultStatusMessage("No services found, can't calculate metric");
            metric.setResultStatus(Metric.RESULT_ERROR);
        } else {

            for (Service service : graph.vertexSet()) {
                double value =
                        ((double) graph.inDegreeOf(service) / (double) graph.vertexSet().size());
                results.put(service.getName(), MathUtils.formatDouble(value));
            }

            metric.setMultipleResults(results);
            metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        }

        return metric;
    }

}
