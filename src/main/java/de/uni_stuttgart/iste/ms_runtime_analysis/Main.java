package de.uni_stuttgart.iste.ms_runtime_analysis;

import java.util.Date;
import java.util.List;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.ArgumentParser;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.DateUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.StringUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.ExporterInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.IntegratorInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Main class for the CLI application
 */
public class Main {

    static final String INTEGRATORS_PACKAGE_PREFIX =
            "de.uni_stuttgart.iste.ms_runtime_analysis.integrators.";

    static final String EXPORTERS_PACKAGE_PREFIX =
            "de.uni_stuttgart.iste.ms_runtime_analysis.exporters.";

    /**
     * Main method for the CLI application
     * 
     * @param args application parameters
     */
    public static void main(String[] args) {

        // Parse command line arguments
        ArgumentParser argumentParser = new ArgumentParser(args);
        Configuration configuration = argumentParser.parse();

        long startTimeFullRun = new Date().getTime();
        Logger.out("Application startup");

        Logger.out("   Analyzing runtime data from "
                + DateUtils.timestampToDate(configuration.getStartDate()) + " to "
                + DateUtils.timestampToDate(configuration.getEndDate()));

        // Empty graph to start with
        DefaultDirectedGraph<Service, DefaultEdge> graph =
                new DefaultDirectedGraph<>(DefaultEdge.class);

        // Find and execute integrators
        long startTime = new Date().getTime();
        Logger.out("   Starting integrators");

        for (String currentIntegrator : configuration.getIntegrators()) {

            String readableParameters = StringUtils.createReadableParameterList(
                    configuration.getIntegratorParams(currentIntegrator));

            long startTimeIntegrator = new Date().getTime();
            Logger.out("      Starting integrator " + currentIntegrator + " with "
                    + (readableParameters.equals("") ? "no " : "") + "parameters"
                    + readableParameters);

            // Search and instantiate the integrator
            Class<? extends IntegratorInterface> integratorClass;
            IntegratorInterface integratorInterface;

            try {
                String classNameSanitized = INTEGRATORS_PACKAGE_PREFIX + currentIntegrator;
                integratorClass =
                        Class.forName(classNameSanitized).asSubclass(IntegratorInterface.class);
                integratorInterface = integratorClass.newInstance();
            } catch (Exception e) {
                Logger.err("      Error: Integrator " + currentIntegrator + " not found, skipping");
                continue;
            }

            graph = integratorInterface.integrateRuntimeData(configuration,
                    configuration.getIntegratorParams(currentIntegrator), graph);

            long endTime = (int) Math.ceil((new Date().getTime() - startTimeIntegrator) / 1000.0);
            Logger.out("      Integrator " + currentIntegrator + " finished after " + endTime
                    + " seconds");

        }

        // Show stats about parsed runtime data
        int operations = 0;
        int calls = 0;

        for (Service service : graph.vertexSet()) {

            operations += service.getOperations().length;

            for (Operation operation : service.getOperations()) {

                for (Long callCount : operation.getCalls().values()) {
                    calls += callCount;
                }

            }

        }

        Logger.out("      Total number of found services: " + graph.vertexSet().size());
        Logger.out("      Total number of found operations: " + operations);
        Logger.out("      Total number of found operation calls: " + calls);

        long endTime = (int) Math.ceil((new Date().getTime() - startTime) / 1000.0);
        Logger.out("   Integrators finished after " + endTime + " seconds");

        // Start metric calculation on the collected runtime data
        startTime = new Date().getTime();
        Logger.out("   Starting metric calculation");

        MetricCalculation metricCalculation = new MetricCalculation();
        List<Metric> metrics = metricCalculation.calculateMetrics(configuration, graph);

        endTime = (int) Math.ceil((new Date().getTime() - startTime) / 1000.0);
        Logger.out("   Metric calculation finished after " + endTime + " seconds");

        // Find and execute exporters
        startTime = new Date().getTime();
        Logger.out("   Starting exporters");

        for (String exporter : configuration.getExporters()) {

            String readableParameters = StringUtils
                    .createReadableParameterList(configuration.getExporterParams(exporter));

            long startTimeExporter = new Date().getTime();
            Logger.out("      Starting exporter " + exporter + " with "
                    + (readableParameters.equals("") ? "no " : "") + "parameters"
                    + readableParameters);

            // Search and instantiate the exporter
            Class<? extends ExporterInterface> exporterClass;
            ExporterInterface exporterInterface;

            try {
                String classNameSanitized = EXPORTERS_PACKAGE_PREFIX + exporter;
                exporterClass =
                        Class.forName(classNameSanitized).asSubclass(ExporterInterface.class);
                exporterInterface = exporterClass.newInstance();
            } catch (Exception e) {
                Logger.err("      Error: Exporter " + exporter + " not found, skipping");
                continue;
            }

            if (!exporterInterface.exportResults(configuration,
                    configuration.getExporterParams(exporter), metrics)) {
                Logger.err("      Error: Exporter " + exporter + " failed");
            }

            endTime = (int) Math.ceil((new Date().getTime() - startTimeExporter) / 1000.0);
            Logger.out("      Exporter " + exporter + " finished after " + endTime + " seconds");

        }

        endTime = (int) Math.ceil((new Date().getTime() - startTime) / 1000.0);
        Logger.out("   Exporters finished after " + endTime + " seconds");

        endTime = (int) Math.ceil((new Date().getTime() - startTimeFullRun) / 1000.0);
        Logger.out("Application finished after " + endTime + " seconds");

    }

}
