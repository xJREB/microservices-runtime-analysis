package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.MathUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric MAIDS
 * Description: Mean number of services which depend on a service / Mean number of services a service depends on
 * Source: S. Schlinger, Benutzung von Laufzeitdaten für die Wartbarkeitsanalyse von Service- und Microservice-basierten Systemen, 2019
 */
public class _016_MAIDS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        int numberOfServices = graph.vertexSet().size();
        int valueRangeEnd = (numberOfServices - 1) < 0 ? 0 : (numberOfServices - 1);
        Metric metric = new Metric("Mean Absolute Importance/Dependence in the System", "MAIDS",
                "Mean number of services which depend on a service / Mean number of services a service depends on",
                "Value range: [0," + valueRangeEnd + "], lower is better",
                Metric.RESULT_TYPE_DOUBLE);

        if (numberOfServices == 0) {
            metric.setResultStatusMessage("No services found, can't calculate metric");
            metric.setResultStatus(Metric.RESULT_ERROR);
        } else {

            double value = MathUtils
                    .formatDouble((double) graph.edgeSet().size() / (double) numberOfServices);

            metric.setSingleResult(value);
            metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);
        }

        return metric;
    }

}
