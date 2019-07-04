package helper;

import java.util.Map;
import models.Metric;

/**
 * Defines helper functions for string processing
 */
public class StringUtils {

    /**
     * Converts a map of parameters to an human readable format
     * 
     * @param parameters parameters to be converted
     * @return human readable parameter string
     */
    public static String createReadableParameterList(Map<String, String> parameters) {

        if (parameters == null) {
            return "";
        }

        StringBuilder readableParameters = new StringBuilder();

        for (String currentKey : parameters.keySet()) {

            readableParameters.append(" ").append(currentKey).append("=")
                    .append(parameters.get(currentKey));

        }

        return readableParameters.toString();
    }

    /**
     * Converts a double value to a defined representation
     * 
     * @param value      value to be converted
     * @param targetType target type of the value
     * @return value in target representation
     */
    public static String doubleToHumanReadable(Double value, int targetType) {

        switch (targetType) {

            case Metric.RESULT_TYPE_PERCENT:
                return (int) (value * 100) + "%";

            case Metric.RESULT_TYPE_INT:
                return String.valueOf(value.intValue());

            case Metric.RESULT_TYPE_DOUBLE:
                return String.valueOf(value);

            default:
                return "Target type unknown";

        }

    }

    /**
     * Escapes a string for usage in markdown files
     * 
     * @param text string to be escaped
     * @return escaped string
     */
    public static String escapeForMarkdown(String text) {
        return text.replace("[", "\\[").replace("]", "\\]").replace("*", "\\*");
    }

}
