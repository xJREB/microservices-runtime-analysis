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
 * Class to calculate the metric DRDS
 * Description: Degree to which a service uses other services in terms of calls
 * Source: S. Schlinger, Benutzung von Laufzeitdaten für die Wartbarkeitsanalyse von Service- und Microservice-basierten Systemen, 2019
 */
public class _018_DRDS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Dynamic Relative Dependence of Service", "DRDS",
                "Degree to which a service uses other services in terms of calls",
                "Value range: (0,1], higher is better", Metric.RESULT_TYPE_PERCENT);
        HashMap<String, Map<String, Double>> nestedResults = new HashMap<>();

        for (Service sourceService : graph.vertexSet()) {

            HashMap<String, Double> subResults = new HashMap<>();
            int callCounter = 0;

            for (Service targetService : graph.vertexSet()) {

                double value = 0;

                for (Operation operation : targetService.getOperations()) {
                    value += operation.getCallCount(sourceService);
                }

                if (value > 0) {
                    subResults.put(targetService.getName(), value);
                    callCounter += value;
                }

            }

            if (callCounter > 0) {

                for (Map.Entry<String, Double> entry : subResults.entrySet()) {
                    entry.setValue(MathUtils.formatDouble(entry.getValue() / callCounter));
                }

                nestedResults.put(sourceService.getName(), subResults);
            }

        }

        metric.setNestedResults(nestedResults);
        metric.setResultStatus(Metric.RESULT_OK_NESTED_RESULTS);

        return metric;
    }

}
