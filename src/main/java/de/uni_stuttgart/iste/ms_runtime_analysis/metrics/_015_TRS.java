package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric TRS
 * Description: Sum of RFO values for all operations
 * Source: M. Perepletchikov, C. Ryan, K. Frampton and Z. Tari, Coupling Metrics for Predicting Maintainability in Service-Oriented Designs, 2007, In 2007 Australian Software Engineering Conference (ASWEC’07). IEEE, 329–340. DOI: https://doi.org/10.1109/ASWEC.2007.17
 */
public class _015_TRS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Total Response for Service", "TRS",
                "Sum of RFO values for all operations", "Value range: [0,INF), lower is better",
                Metric.RESULT_TYPE_INT);
        int counter = 0;

        for (Service service : graph.vertexSet()) {

            for (Operation operation : service.getOperations()) {
                counter += operation.getResponsesForOperation().length;
            }

        }

        metric.setSingleResult((double) counter);
        metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        return metric;
    }

}
