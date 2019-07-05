package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric NS
 * Description: Number of services in the system
 * Source: B. Shim, S. Choue, S. Kim and S. Park, A Design Quality Model for Service-Oriented Architecture, 2008, In 2008 15th Asia-Pacific Software Engineering Conference. IEEE, 403–410. DOI: https://doi.org/10.1109/APSEC.2008.32
 */
public class _005_NS implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Number of Services", "NS", "Number of services in the system",
                "Value range: [0,INF), lower is better", Metric.RESULT_TYPE_INT);

        metric.setSingleResult((double) graph.vertexSet().size());
        metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        return metric;
    }

}
