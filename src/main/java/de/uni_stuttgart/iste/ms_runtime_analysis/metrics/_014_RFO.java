package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric RFO
 * Description: Cardinality of the set of other service interfaces that can be executed in response to invocations with all possible parameters
 * Source: M. Perepletchikov, C. Ryan, K. Frampton and Z. Tari, Coupling Metrics for Predicting Maintainability in Service-Oriented Designs, 2007, In 2007 Australian Software Engineering Conference (ASWEC’07). IEEE, 329–340. DOI: https://doi.org/10.1109/ASWEC.2007.17
 */
public class _014_RFO implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Response for Operation", "RFO",
                "Cardinality of the set of other service interfaces that can be executed in response to invocations with all possible parameters",
                "Value range: [0,INF), lower is better", Metric.RESULT_TYPE_INT);
        HashMap<String, Map<String, Double>> nestedResults = new HashMap<>();
        ArrayList<String> rfos = new ArrayList<>();

        for (Service service : graph.vertexSet()) {

            if (service.getOperations().length < 1) {
                continue;
            }

            HashMap<String, Double> subResults = new HashMap<>();

            for (Operation operation : service.getOperations()) {
                subResults.put(Arrays.toString(operation.getParameters()),
                        (double) operation.getResponsesForOperation().length);

                if (operation.getResponsesForOperation().length > 0) {
                    rfos.add(service.getName() + " (" + Arrays.toString(operation.getParameters())
                            + "): " + Arrays.toString(operation.getResponsesForOperation()));
                }
            }

            nestedResults.put(service.getName(), subResults);
        }

        metric.setNestedResults(nestedResults);
        metric.setResultDetails(rfos.toArray(new String[0]));
        metric.setResultStatus(Metric.RESULT_OK_NESTED_RESULTS);

        return metric;
    }

}
