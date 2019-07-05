package de.uni_stuttgart.iste.ms_runtime_analysis.exporters;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.StringUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.ExporterInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;

/**
 * Exports the calculated metrics in an human readable format
 */
public class human_readable implements ExporterInterface {

    /**
     * Export the calculated metrics in an human readable format
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

        StringBuilder output = new StringBuilder();

        for (Metric metric : metrics) {

            // Spacing to last entry
            if (!output.toString().equals("")) {
                output.append("\r\n\r\n\r\n");
            }

            // Name, Abbreviation and Underlining
            String nameAbbreviation =
                    metric.getLongName() + " (" + metric.getAbbreviation() + ")" + "\r\n";

            output.append(nameAbbreviation);

            for (int i = 0; i < nameAbbreviation.length() - 2; i++) {
                output.append("=");
            }

            output.append("\r\n\r\n");

            // Description
            output.append("Description: ");
            output.append(metric.getDescription());
            output.append("\r\n");

            // Interpretation
            output.append("Interpretation: ");
            output.append((metric.getResultType() == Metric.RESULT_TYPE_PERCENT)
                    ? metric.getInterpretation().replace(",1)", ",100)").replace(",1]", ",100]")
                    : metric.getInterpretation());
            output.append("\r\n\r\n");

            // Results
            switch (metric.getResultStatus()) {

                case Metric.RESULT_ERROR:
                    output.append("Error: ");
                    output.append(metric.getResultStatusMessage());
                    break;

                case Metric.RESULT_OK_SINGLE_RESULT:
                    output.append("Result: ");
                    output.append(StringUtils.doubleToHumanReadable(metric.getSingleResult(),
                            metric.getResultType()));
                    break;

                case Metric.RESULT_OK_MULTIPLE_RESULTS:
                    output.append("Results:\r\n");

                    for (Map.Entry<String, Double> result : metric.getMultipleResults()
                            .entrySet()) {

                        output.append("\r\n");
                        output.append("    ");
                        output.append(result.getKey());
                        output.append(": ");
                        output.append(StringUtils.doubleToHumanReadable(result.getValue(),
                                metric.getResultType()));

                    }

                    break;

                case Metric.RESULT_OK_NESTED_RESULTS:
                    output.append("Results:");

                    for (Map.Entry<String, Map<String, Double>> result : metric.getNestedResults()
                            .entrySet()) {

                        output.append("\r\n\r\n");
                        output.append("    ");
                        output.append(result.getKey());
                        output.append(":");

                        for (Map.Entry<String, Double> subResult : result.getValue().entrySet()) {

                            output.append("\r\n");
                            output.append("        ");
                            output.append(subResult.getKey());
                            output.append(": ");
                            output.append(StringUtils.doubleToHumanReadable(subResult.getValue(),
                                    metric.getResultType()));

                        }

                    }

                    break;

                case Metric.RESULT_NOT_PROCESSED_YET:
                    output.append("Error: Not calculated");
                    break;

            }

            // Details
            if (metric.getResultDetails() != null) {

                output.append("\r\n\r\n");
                output.append("Details:\r\n");

                for (String detail : metric.getResultDetails()) {
                    output.append("\r\n");
                    output.append("    ");
                    output.append(detail);
                }

            }



        }

        // Write result to file
        try (PrintWriter writer = new PrintWriter(params.get("filename").toString(), "UTF-8")) {
            writer.print(output);
        } catch (Exception e) {
            Logger.err("         Error: Could not write to file \""
                    + params.get("filename").toString() + "\": " + e.getMessage());
            return false;
        }

        return true;
    }

}
