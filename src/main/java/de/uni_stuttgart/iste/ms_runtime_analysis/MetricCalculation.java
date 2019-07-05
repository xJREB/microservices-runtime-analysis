package de.uni_stuttgart.iste.ms_runtime_analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.reflections.Reflections;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.MetricInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Class to manage the calculation of the metrics
 */
public class MetricCalculation {

    static final String METRICS_PACKAGE_NAME = "de.uni_stuttgart.iste.ms_runtime_analysis.metrics";

    /**
     * Calculates the metrics
     * 
     * @param configuration application parameters
     * @param graph         collected runtime data in canonical format
     * @return list of calculated metrics
     */
    public List<Metric> calculateMetrics(Configuration configuration,
            DefaultDirectedGraph<Service, DefaultEdge> graph) {

        ArrayList<Metric> metrics = new ArrayList<>();

        // Find metric classes
        Reflections reflections = new Reflections(METRICS_PACKAGE_NAME);
        Set<Class<? extends MetricInterface>> metricClasses =
                reflections.getSubTypesOf(MetricInterface.class);
        List<Class> sortedMetricClasses = new ArrayList<>(metricClasses);
        sortedMetricClasses.sort(Comparator.comparing(Class::toString));

        // Run each metric calculation
        MetricInterface metricInterface;

        for (Class metricClass : sortedMetricClasses) {

            try {
                metricInterface = (MetricInterface) metricClass.newInstance();
                metrics.add(metricInterface.calculateMetric(graph));
            } catch (Exception e) {
                Logger.err(
                        "   Error: Could not load metric " + metricClass.getName() + ", skipping");
            }

        }

        // List calculated metrics
        int success = 0;
        int errors = 0;
        int incomplete = 0;

        for (Metric currentMetric : metrics) {

            Logger.debug("      " + currentMetric.getLongName() + " ("
                    + currentMetric.getAbbreviation() + ")");
            Logger.debug("         Description: " + currentMetric.getDescription());

            switch (currentMetric.getResultStatus()) {

                case Metric.RESULT_ERROR:
                    Logger.debug("         Error: " + currentMetric.getResultStatusMessage());
                    errors++;
                    break;

                case Metric.RESULT_OK_SINGLE_RESULT:
                    Logger.debug("         Result: " + currentMetric.getSingleResult());
                    if (currentMetric.getResultDetails() != null) {
                        Logger.debug("         Details: "
                                + Arrays.toString(currentMetric.getResultDetails()));
                    }
                    Logger.debug("         Interpretation: " + currentMetric.getInterpretation());
                    success++;
                    break;

                case Metric.RESULT_OK_MULTIPLE_RESULTS:
                    Logger.debug(
                            "         Results: " + currentMetric.getMultipleResults().toString());
                    if (currentMetric.getResultDetails() != null) {
                        Logger.debug("         Details: "
                                + Arrays.toString(currentMetric.getResultDetails()));
                    }
                    Logger.debug("         Interpretation: " + currentMetric.getInterpretation());
                    success++;
                    break;

                case Metric.RESULT_OK_NESTED_RESULTS:
                    Logger.debug(
                            "         Results: " + currentMetric.getNestedResults().toString());
                    if (currentMetric.getResultDetails() != null) {
                        Logger.debug("         Details: "
                                + Arrays.toString(currentMetric.getResultDetails()));
                    }
                    Logger.debug("         Interpretation: " + currentMetric.getInterpretation());
                    success++;
                    break;

                case Metric.RESULT_NOT_PROCESSED_YET:
                    Logger.debug("         Result: Not calculated");
                    incomplete++;
                    break;

            }

        }

        Logger.out("      Processed " + metrics.size() + " metrics, " + success + " successfully, "
                + errors + " errors, " + incomplete + " skipped");

        return metrics;
    }

}
