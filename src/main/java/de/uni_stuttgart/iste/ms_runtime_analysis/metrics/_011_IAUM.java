package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.MathUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric IAUM
 * Description: Ratio between number of services and message types
 * Source: B. Shim, S. Choue, S. Kim and S. Park, A Design Quality Model for Service-Oriented Architecture, 2008, In 2008 15th Asia-Pacific Software Engineering Conference. IEEE, 403–410. DOI: https://doi.org/10.1109/APSEC.2008.32
 */
public class _011_IAUM implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Inverse of Average Number of Used Message", "IAUM",
                "Ratio between number of services and message types",
                "Value range: (0,1], very low values should be avoided", Metric.RESULT_TYPE_DOUBLE);

        double value = 0;

        for (Service service : graph.vertexSet()) {
            value += service.getOperations().length;
        }

        if (value == 0) {
            metric.setResultStatusMessage("No operations found, can't calculate metric");
            metric.setResultStatus(Metric.RESULT_ERROR);
        } else {

            int numberOfServices = graph.vertexSet().size();
            value = (double) (numberOfServices) / value;

            metric.setSingleResult(MathUtils.formatDouble(value));
            metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        }

        return metric;
    }

}
