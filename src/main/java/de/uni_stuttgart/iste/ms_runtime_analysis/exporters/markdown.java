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
 * Exports the calculated metrics into the markdown format
 */
public class markdown implements ExporterInterface {

    /**
     * Export the calculated metrics into the markdown format
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

            // Name and Abbreviation
            String nameAbbreviation = "# " + metric.getLongName() + " (" + metric.getAbbreviation()
                    + ")" + "\r\n\r\n";
            output.append(nameAbbreviation);

            // Description
            output.append("## Description\r\n");
            output.append(metric.getDescription());
            output.append("\r\n\r\n");

            // Interpretation
            output.append("## Interpretation\r\n");
            output.append((metric.getResultType() == Metric.RESULT_TYPE_PERCENT)
                    ? metric.getInterpretation().replace(",1)", ",100)").replace(",1]", ",100]")
                    : metric.getInterpretation());
            output.append("\r\n\r\n");

            // Results
            switch (metric.getResultStatus()) {

                case Metric.RESULT_ERROR:
                    output.append("## Error\r\n");
                    output.append(StringUtils.escapeForMarkdown(metric.getResultStatusMessage()));
                    break;

                case Metric.RESULT_OK_SINGLE_RESULT:
                    output.append("## Result\r\n");
                    output.append(StringUtils.escapeForMarkdown(StringUtils.doubleToHumanReadable(
                            metric.getSingleResult(), metric.getResultType())));
                    break;

                case Metric.RESULT_OK_MULTIPLE_RESULTS:
                    output.append("## Results\r\n");

                    for (Map.Entry<String, Double> result : metric.getMultipleResults()
                            .entrySet()) {

                        output.append("\r\n");
                        output.append("- ");
                        output.append(StringUtils.escapeForMarkdown(result.getKey()));
                        output.append(": ");
                        output.append(StringUtils.escapeForMarkdown(StringUtils
                                .doubleToHumanReadable(result.getValue(), metric.getResultType())));

                    }

                    break;

                case Metric.RESULT_OK_NESTED_RESULTS:
                    output.append("## Results");

                    for (Map.Entry<String, Map<String, Double>> result : metric.getNestedResults()
                            .entrySet()) {

                        output.append("\r\n\r\n");
                        output.append("- ");
                        output.append(StringUtils.escapeForMarkdown(result.getKey()));
                        output.append(":");

                        for (Map.Entry<String, Double> subResult : result.getValue().entrySet()) {

                            output.append("\r\n");
                            output.append("    - ");
                            output.append(StringUtils.escapeForMarkdown(subResult.getKey()));
                            output.append(": ");
                            output.append(
                                    StringUtils.escapeForMarkdown(StringUtils.doubleToHumanReadable(
                                            subResult.getValue(), metric.getResultType())));

                        }

                    }

                    break;

                case Metric.RESULT_NOT_PROCESSED_YET:
                    output.append("## Error\r\nNot calculated");
                    break;

            }

            // Details
            if (metric.getResultDetails() != null) {

                output.append("\r\n\r\n");
                output.append("## Details\r\n");

                for (String detail : metric.getResultDetails()) {
                    output.append("\r\n");
                    output.append("- ");
                    output.append(StringUtils.escapeForMarkdown(detail));
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
