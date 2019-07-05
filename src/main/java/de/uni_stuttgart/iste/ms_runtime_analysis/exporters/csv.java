package de.uni_stuttgart.iste.ms_runtime_analysis.exporters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.ExporterInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;

/**
 * Exports the calculated metrics into the csv format
 */
public class csv implements ExporterInterface {

    /**
     * Export the calculated metrics into the csv format
     * 
     * @param configuration application parameters
     * @param params        exporter parameters
     * @param metrics       calculated metrics
     * @return true if the export was successful, else false
     */
    @Override
    public Boolean exportResults(Configuration configuration, Map params, List<Metric> metrics) {

        // Check if required params are set
        if ((params == null) || (!params.containsKey("filename"))) {
            Logger.err(
                    "         Error: Missing required parameter \"filename\", continuing with next exporter");
            return false;
        }

        // Write results to file
        try (FileWriter file = new FileWriter(params.get("filename").toString());
                CSVPrinter csvPrinter = new CSVPrinter(file,
                        CSVFormat.DEFAULT.withHeader("Name", "Abbreviation", "Description",
                                "Interpretation", "Result Status", "Result 1", "Result 2",
                                "Result 3", "Details"))) {

            for (Metric metric : metrics) {

                // Details
                String details = (metric.getResultDetails() != null)
                        ? String.join(" / ", metric.getResultDetails())
                        : "";

                // Results
                switch (metric.getResultStatus()) {

                    case Metric.RESULT_ERROR:

                        // Error
                        csvPrinter.printRecord(metric.getLongName(), metric.getAbbreviation(),
                                metric.getDescription(), metric.getInterpretation(), "Error",
                                metric.getResultStatusMessage(), "", "", details);
                        break;

                    case Metric.RESULT_OK_SINGLE_RESULT:

                        // Result
                        csvPrinter.printRecord(metric.getLongName(), metric.getAbbreviation(),
                                metric.getDescription(), metric.getInterpretation(),
                                "Single Result", String.valueOf(metric.getSingleResult()), "", "",
                                details);
                        break;

                    case Metric.RESULT_OK_MULTIPLE_RESULTS:

                        // Results
                        for (Map.Entry<String, Double> result : metric.getMultipleResults()
                                .entrySet()) {
                            csvPrinter.printRecord(metric.getLongName(), metric.getAbbreviation(),
                                    metric.getDescription(), metric.getInterpretation(),
                                    "Multiple Results", result.getKey(),
                                    String.valueOf(result.getValue()), "", details);
                        }

                        break;

                    case Metric.RESULT_OK_NESTED_RESULTS:

                        // Results
                        for (Map.Entry<String, Map<String, Double>> result : metric
                                .getNestedResults().entrySet()) {

                            // Result
                            for (Map.Entry<String, Double> subResult : result.getValue()
                                    .entrySet()) {
                                csvPrinter.printRecord(metric.getLongName(),
                                        metric.getAbbreviation(), metric.getDescription(),
                                        metric.getInterpretation(), "Nested Results",
                                        result.getKey(), subResult.getKey(),
                                        String.valueOf(subResult.getValue()), details);
                            }

                        }

                        break;

                    case Metric.RESULT_NOT_PROCESSED_YET:

                        // Error
                        csvPrinter.printRecord(metric.getLongName(), metric.getAbbreviation(),
                                metric.getDescription(), metric.getInterpretation(), "Error",
                                "Not calculated", "", "", details);
                        break;

                }

            }

            csvPrinter.flush();
            file.flush();
        } catch (IOException e) {
            Logger.err("         Error: Could not write to file \""
                    + params.get("filename").toString() + "\": " + e.getMessage());
            return false;
        }

        return true;
    }

}
