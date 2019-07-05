package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.HashMap;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.MathUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric DRISS Description: Degree to which other services depend on a
 * service in terms of calls compared to all calls system-wide Source: S. Schlinger, Benutzung von
 * Laufzeitdaten für die Wartbarkeitsanalyse von Service- und Microservice-basierten Systemen, 2019
 */
public class _021_DRISS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * 
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Dynamic Relative Importance of Service in the System", "DRISS",
                "Degree to which other services depend on a service in terms of calls compared to all calls system-wide",
                "Value range: (0,1], lower is better", Metric.RESULT_TYPE_PERCENT);
        HashMap<String, Map<String, Double>> nestedResults = new HashMap<>();
        int callCounterSystem = 0;

        for (Service service : graph.vertexSet()) {

            HashMap<String, Double> subResults = new HashMap<>();
            int callCounter = 0;

            for (Operation operation : service.getOperations()) {

                for (Map.Entry<Service, Long> call : operation.getCalls().entrySet()) {

                    if (subResults.containsKey(call.getKey().getName())) {
                        subResults.put(call.getKey().getName(),
                                subResults.get(call.getKey().getName()) + (double) call.getValue());
                    } else {
                        subResults.put(call.getKey().getName(), (double) call.getValue());
                    }

                    callCounter += call.getValue();
                }

            }

            if (callCounter > 0) {
                nestedResults.put(service.getName(), subResults);
                callCounterSystem += callCounter;
            }

        }

        for (Map.Entry<String, Map<String, Double>> nestedResult : nestedResults.entrySet()) {
            for (Map.Entry<String, Double> entry : nestedResult.getValue().entrySet()) {
                if (callCounterSystem != 0) {
                    entry.setValue(MathUtils.formatDouble(entry.getValue() / callCounterSystem));
                }
            }
        }

        metric.setNestedResults(nestedResults);
        metric.setResultStatus(Metric.RESULT_OK_NESTED_RESULTS);

        return metric;
    }

}
