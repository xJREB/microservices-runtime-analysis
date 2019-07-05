package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.MathUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric SCP
 * Description: Fraction of services which are composite
 * Source: M. Hirzalla, J. Cleland-Huang and A. Arsanjani, A Metrics Suite for Evaluating Flexibility and Complexity in Service Oriented Architectures, 2009, In Service-Oriented Computing – ICSOC 2008 Workshops: ICSOC 2008 International Workshops, Sydney, Australia, December 1st, 2008, Revised Selected Papers, Bernd J. Krämer, Kwei-Jay Lin, and Priya Narasimhan (Eds.). Lecture Notes in Computer Science, Vol. 4749. Springer Berlin Heidelberg, Berlin, Heidelberg, 41–52. DOI: https://doi.org/10.1007/978-3-642-01247-1_5
 */
public class _010_SCP implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        Metric metric = new Metric("Service Composition Pattern", "SCP",
                "Fraction of services which are composite", "Value range: [0,1], lower is better",
                Metric.RESULT_TYPE_PERCENT);

        if (graph.vertexSet().isEmpty()) {
            metric.setResultStatusMessage("No services found, can't calculate metric");
            metric.setResultStatus(Metric.RESULT_ERROR);
        } else {

            double value = 0;

            for (Service service : graph.vertexSet()) {
                if (!graph.outgoingEdgesOf(service).isEmpty()) {
                    value++;
                }
            }

            int numberOfServices = graph.vertexSet().size();
            value = value / (double) (numberOfServices);

            metric.setSingleResult(MathUtils.formatDouble(value));
            metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        }

        return metric;
    }

}
