package de.uni_stuttgart.iste.ms_runtime_analysis.metrics;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.GraphUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to calculate the metric CSD
 * Description: Number of cycles in the dependencies
 * Source: S. Schlinger, Benutzung von Laufzeitdaten für die Wartbarkeitsanalyse von Service- und Microservice-basierten Systemen, 2019
 */
public class _022_CSD implements MetricInterface {

    /**
     * Calculates the metric from the provided runtime data
     * @param graph runtime data to be used
     * @return calculated metric
     */
    @Override
    public Metric calculateMetric(DefaultDirectedGraph<Service, DefaultEdge> graph) {

        long longValueRangeEnd = GraphUtils.maxCyclesInCompleteGraph(graph.vertexSet().size());
        Metric metric = new Metric("Cyclic Service Dependencies", "CSD",
                "Number of cycles in the dependencies",
                "Value range: [0," + longValueRangeEnd + "], lower is better",
                Metric.RESULT_TYPE_INT);

        String[] cycles = GraphUtils.getCycles(graph);

        metric.setSingleResult((double) cycles.length);
        metric.setResultDetails(cycles);
        metric.setResultStatus(Metric.RESULT_OK_SINGLE_RESULT);

        return metric;
    }

}
