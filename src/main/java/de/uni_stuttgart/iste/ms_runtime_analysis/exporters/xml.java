package de.uni_stuttgart.iste.ms_runtime_analysis.exporters;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.ExporterInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;

/**
 * Exports the calculated metrics into the xml format
 */
public class xml implements ExporterInterface {

    /**
     * Export the calculated metrics into the xml format
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

        // Create XML document builder
        Document doc;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
        } catch (Exception e) {
            Logger.err(
                    "         Error: Could not create xml document builder, continuing with next exporter");
            return false;
        }

        // Metrics
        Element metricsElement = doc.createElement("metrics");
        doc.appendChild(metricsElement);

        for (Metric metric : metrics) {

            // Metric
            Element metricElement = doc.createElement("metric");

            // Name
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(metric.getLongName()));
            metricElement.appendChild(nameElement);

            // Abbreviation
            Element abbreviationElement = doc.createElement("abbreviation");
            abbreviationElement.appendChild(doc.createTextNode(metric.getAbbreviation()));
            metricElement.appendChild(abbreviationElement);

            // Description
            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(metric.getDescription()));
            metricElement.appendChild(descriptionElement);

            // Interpretation
            Element interpretationElement = doc.createElement("interpretation");
            interpretationElement.appendChild(doc.createTextNode(metric.getInterpretation()));
            metricElement.appendChild(interpretationElement);

            // Results
            switch (metric.getResultStatus()) {

                case Metric.RESULT_ERROR:

                    // Error
                    Element errorElement = doc.createElement("error");
                    errorElement.appendChild(doc.createTextNode(metric.getResultStatusMessage()));
                    metricElement.appendChild(errorElement);

                    break;

                case Metric.RESULT_OK_SINGLE_RESULT:

                    // Result
                    Element resultElement = doc.createElement("result");
                    resultElement.appendChild(
                            doc.createTextNode(String.valueOf(metric.getSingleResult())));
                    metricElement.appendChild(resultElement);

                    break;

                case Metric.RESULT_OK_MULTIPLE_RESULTS:

                    // Results
                    Element resultsElement = doc.createElement("results");

                    for (Map.Entry<String, Double> result : metric.getMultipleResults()
                            .entrySet()) {

                        // Result
                        resultElement = doc.createElement("result");

                        // Name
                        nameElement = doc.createElement("name");
                        nameElement.appendChild(doc.createTextNode(result.getKey()));
                        resultElement.appendChild(nameElement);

                        // Value
                        Element valueElement = doc.createElement("value");
                        valueElement
                                .appendChild(doc.createTextNode(String.valueOf(result.getValue())));
                        resultElement.appendChild(valueElement);

                        // Result
                        resultsElement.appendChild(resultElement);

                    }

                    // Results
                    metricElement.appendChild(resultsElement);

                    break;

                case Metric.RESULT_OK_NESTED_RESULTS:

                    // Results
                    resultsElement = doc.createElement("results");

                    for (Map.Entry<String, Map<String, Double>> result : metric.getNestedResults()
                            .entrySet()) {

                        // Result
                        resultElement = doc.createElement("result");

                        // Name
                        nameElement = doc.createElement("name");
                        nameElement.appendChild(doc.createTextNode(result.getKey()));
                        resultElement.appendChild(nameElement);

                        for (Map.Entry<String, Double> subResult : result.getValue().entrySet()) {

                            // SubResult
                            Element subResultElement = doc.createElement("subresult");

                            // Name
                            nameElement = doc.createElement("name");
                            nameElement.appendChild(doc.createTextNode(subResult.getKey()));
                            subResultElement.appendChild(nameElement);

                            // Value
                            Element valueElement = doc.createElement("value");
                            valueElement.appendChild(
                                    doc.createTextNode(String.valueOf(subResult.getValue())));
                            subResultElement.appendChild(valueElement);

                            // Subresult
                            resultElement.appendChild(subResultElement);

                        }

                        // Result
                        resultsElement.appendChild(resultElement);

                    }

                    // Results
                    metricElement.appendChild(resultsElement);

                    break;

                case Metric.RESULT_NOT_PROCESSED_YET:

                    // Error
                    errorElement = doc.createElement("error");
                    errorElement.appendChild(doc.createTextNode("Not calculated"));
                    metricElement.appendChild(errorElement);

                    break;

            }

            // Details
            if (metric.getResultDetails() != null) {

                // Details
                Element detailsElement = doc.createElement("details");

                for (String detail : metric.getResultDetails()) {

                    // Detail
                    Element detailElement = doc.createElement("detail");
                    detailElement.appendChild(doc.createTextNode(detail));
                    detailsElement.appendChild(detailElement);

                }

                // Details
                metricElement.appendChild(detailsElement);

            }

            // Metric
            metricsElement.appendChild(metricElement);

        }

        // Write result to file
        try {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(params.get("filename").toString()));
            transformer.transform(source, result);

        } catch (Exception e) {
            Logger.err("         Error: Could not write to file \""
                    + params.get("filename").toString() + "\": " + e.getMessage());
            return false;
        }

        return true;
    }

}
