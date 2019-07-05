package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.MathUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric SCF
 * Description: Indicates the overall coupling in the system, the sum over all single coupling values is set in relation with the maximum couplings that could occur in the system
 * Source: Z. Qingqing and L. Xinke, Complexity Metrics for Service-Oriented Systems, 2009, In 2009 Second International Symposium on Knowledge Acquisition and Modeling, Vol. 3. IEEE, 375–378. DOI: https://doi.org/10.1109/KAM.2009.90
 */
public class _008_SCF implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Service Coupling Factor", "SCF",
                "Indicates the overall coupling in the system, the sum over all single coupling values is set in relation with the maximum couplings that could occur in the system",
                "Value range: [0,1], lower is better", Metric.RESULT_TYPE_DOUBLE);

        if (graph.vertexSet().isEmpty()) {
            metric.setResultStatusMessage("No services found, can't calculate metric");
            metric.setResultStatus(Metric.RESULT_ERROR);
        } else {

            double value = 0;

            for (Service service : graph.vertexSet()) {
                value += graph.outgoingEdgesOf(service).size();
            }

            int numberOfServices = graph.vertexSet().size();
            value = value / (double) ((numberOfServices * numberOfServices) - numberOfServices);

            metric.setSingleResult(MathUtils.formatDouble(value));
            metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        }

        return metric;
    }

}
