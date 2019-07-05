package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.ArrayList;
import java.util.Arrays;
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
 * Class to calculate the metric SIDC
 * Description: Quantifies cohesion of a service based on the number of operations sharing the same input parameter types
 * Source: M. Perepletchikov, C. Ryan and K. Frampton, Cohesion Metrics for Predicting Maintainability of Service-Oriented Software, 2007, In Seventh International Conference on Quality Software (QSIC 2007). IEEE, 328–335. DOI: https://doi.org/10.1109/QSIC.2007.4385516
 */
public class _013_SIDC implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Service Interface Data Cohesion", "SIDC",
                "Quantifies cohesion of a service based on the number of operations sharing the same input parameter types",
                "Value range: [0,1], higher is better", Metric.RESULT_TYPE_PERCENT);
        HashMap<String, Double> results = new HashMap<>();

        for (Service service : graph.vertexSet()) {

            HashMap<String, Integer> operationParameterMap = new HashMap<>();
            int numberOfOperations = 0;

            for (Operation operation : service.getOperations()) {
                ArrayList<String> parameterNames = new ArrayList<>();

                for (String parameter : operation.getParameters()) {

                    String[] parameterParts = parameter.split(":", 2);
                    if (parameterParts.length != 2) {
                        continue;
                    }

                    parameterNames.add(parameterParts[0]);
                }

                if (!parameterNames.isEmpty()) {

                    String[] sortedParameterNames = parameterNames.toArray(new String[0]);
                    Arrays.sort(sortedParameterNames);
                    String operationParametersSerialized = Arrays.toString(sortedParameterNames);

                    if (operationParameterMap.containsKey(operationParametersSerialized)) {
                        operationParameterMap.put(operationParametersSerialized,
                                operationParameterMap.get(operationParametersSerialized) + 1);
                    } else {
                        operationParameterMap.put(operationParametersSerialized, 1);
                    }

                    numberOfOperations++;
                }
            }

            if (numberOfOperations > 0) {

                int commonParameterOperations = 0;

                for (Map.Entry<String, Integer> operationParameterMapEntry : operationParameterMap
                        .entrySet()) {

                    if ((operationParameterMapEntry.getValue() > 1)
                            || (operationParameterMap.size() == 1)) {
                        commonParameterOperations += operationParameterMapEntry.getValue();
                    }

                }

                results.put(service.getName(), MathUtils.formatDouble(
                        (double) commonParameterOperations / (double) numberOfOperations));
            }

        }

        metric.setMultipleResults(results);
        metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        return metric;
    }

}
