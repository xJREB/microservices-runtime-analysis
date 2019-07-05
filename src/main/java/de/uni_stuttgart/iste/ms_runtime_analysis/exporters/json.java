package de.uni_stuttgart.iste.ms_runtime_analysis.exporters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.ExporterInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;

/**
 * Exports the calculated metrics into the json format
 */
public class json implements ExporterInterface {

    /**
     * Export the calculated metrics into the json format
     * 
     * @param configuration application parameters
     * @param params        exporter parameters
     * @param metrics       calculated metrics
     * @return true if the export was successful, else false
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean exportResults(Configuration configuration, Map params, List<Metric> metrics) {

        // Check if required params are set
        if ((params == null) || (!params.containsKey("filename"))) {
            Logger.err(
                    "         Error: Missing required parameter \"filename\", continuing with next exporter");
            return false;
        }

        JSONObject json = new JSONObject();

        // Metrics
        JSONArray metricsElement = new JSONArray();

        for (Metric metric : metrics) {

            // Metric
            JSONObject metricElement = new JSONObject();

            // Name
            metricElement.put("name", metric.getLongName());

            // Abbreviation
            metricElement.put("abbreviation", metric.getAbbreviation());

            // Description
            metricElement.put("description", metric.getDescription());

            // Interpretation
            metricElement.put("interpretation", metric.getInterpretation());

            // Results
            switch (metric.getResultStatus()) {

                case Metric.RESULT_ERROR:

                    // Error
                    metricElement.put("error", metric.getResultStatusMessage());
                    break;

                case Metric.RESULT_OK_SINGLE_RESULT:

                    // Result
                    metricElement.put("result", String.valueOf(metric.getSingleResult()));
                    break;

                case Metric.RESULT_OK_MULTIPLE_RESULTS:

                    // Results
                    JSONObject resultsElement = new JSONObject();

                    for (Map.Entry<String, Double> result : metric.getMultipleResults()
                            .entrySet()) {

                        // Result element
                        resultsElement.put(result.getKey(), String.valueOf(result.getValue()));

                    }

                    // Results
                    metricElement.put("results", resultsElement);

                    break;

                case Metric.RESULT_OK_NESTED_RESULTS:

                    // Results
                    resultsElement = new JSONObject();

                    for (Map.Entry<String, Map<String, Double>> result : metric.getNestedResults()
                            .entrySet()) {

                        // Result
                        JSONObject resultElement = new JSONObject();

                        for (Map.Entry<String, Double> subResult : result.getValue().entrySet()) {

                            resultElement.put(subResult.getKey(),
                                    String.valueOf(subResult.getValue()));

                        }

                        // Result
                        resultsElement.put(result.getKey(), resultElement);

                    }

                    // Results
                    metricElement.put("results", resultsElement);

                    break;

                case Metric.RESULT_NOT_PROCESSED_YET:

                    // Error
                    metricElement.put("error", "Not calculated");
                    break;

            }

            // Details
            if (metric.getResultDetails() != null) {

                // Details
                JSONArray detailsElement = new JSONArray();
                Collections.addAll(detailsElement, metric.getResultDetails());
                metricElement.put("details", detailsElement);

            }

            // Metric
            metricsElement.add(metricElement);

        }

        json.put("metrics", metricsElement);

        // Write result to file
        try (FileWriter file = new FileWriter(params.get("filename").toString())) {
            file.write(new GsonBuilder().setPrettyPrinting().create().toJson(json));
            file.flush();
        } catch (IOException e) {
            Logger.err("         Error: Could not write to file \""
                    + params.get("filename").toString() + "\": " + e.getMessage());
            return false;
        }

        return true;
    }

}
