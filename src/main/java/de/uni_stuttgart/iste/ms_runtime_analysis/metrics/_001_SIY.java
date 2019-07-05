package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric SIY
 * Description: Count of pairs of services which depend on each other
 * Source: D. Rud, A. Schmietendorf and R. R. Dumke, Product Metrics for Service-Oriented Infrastructures, 2006, In IWSM/MetriKon.
 */
public class _001_SIY implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        int numberOfServices = graph.vertexSet().size();
        int valueRangeEnd =
                (numberOfServices - 1) < 0 ? 0 : (numberOfServices * (numberOfServices - 1)) / 2;
        Metric metric = new Metric("Services Interdependence in the System", "SIY",
                "Count of pairs of services which depend on each other",
                "Value range: [0," + valueRangeEnd + "], lower is better", Metric.RESULT_TYPE_INT);

        double value = 0;

        for (Service service : graph.vertexSet()) {

            for (DefaultEdge edge : graph.outgoingEdgesOf(service)) {

                if (graph.containsEdge(graph.getEdgeTarget(edge), service)) {
                    value++;
                }

            }
        }

        metric.setSingleResult(value / 2);
        metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        return metric;
    }

}
