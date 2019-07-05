package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import java.util.ArrayList;
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
 * Class to calculate the metric SIUC
 * Description: Quantifies cohesion of a service based on the number of operations invoked by every client
 * Source: M. Perepletchikov, C. Ryan and K. Frampton, Cohesion Metrics for Predicting Maintainability of Service-Oriented Software, 2007, In Seventh International Conference on Quality Software (QSIC 2007). IEEE, 328–335. DOI: https://doi.org/10.1109/QSIC.2007.4385516
 */
public class _012_SIUC implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Service Interface Usage Cohesion", "SIUC",
                "Quantifies cohesion of a service based on the number of operations invoked by every client",
                "Value range: (0,1], higher is better", Metric.RESULT_TYPE_PERCENT);
        HashMap<String, Double> results = new HashMap<>();

        for (Service service : graph.vertexSet()) {

            ArrayList<Service> callers = new ArrayList<>();
            int numberOfOperations = service.getOperations().length;
            int numberOfCalls = 0;

            for (Operation operation : service.getOperations()) {

                for (Map.Entry<Service, Long> call : operation.getCalls().entrySet()) {

                    if (!callers.contains(call.getKey())) {
                        callers.add(call.getKey());
                    }

                    numberOfCalls++;
                }

            }

            if ((!callers.isEmpty()) && (numberOfOperations > 0)) {
                results.put(service.getName(), MathUtils.formatDouble((double) numberOfCalls
                        / ((double) callers.size() * (double) numberOfOperations)));
            }
        }

        metric.setMultipleResults(results);
        metric.setResultStatus(Metric.RESULT_OK_MULTIPLE_RESULTS);

        return metric;
    }

}
