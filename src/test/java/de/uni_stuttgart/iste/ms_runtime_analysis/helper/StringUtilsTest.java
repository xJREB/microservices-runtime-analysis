package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;

public class StringUtilsTest {

    @Test
    public void createReadableParameterList() {

        // Empty parameter
        String parameters = StringUtils.createReadableParameterList(null);
        assertEquals("", parameters);

        // Single parameter
        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("key1", "value1");
        parameters = StringUtils.createReadableParameterList(parameterList);
        assertEquals(" key1=value1", parameters);

        // Multiple parameters
        parameterList = new HashMap<>();
        parameterList.put("key1", "value1");
        parameterList.put("key2", "value2");
        parameterList.put("key3", "value3");
        parameters = StringUtils.createReadableParameterList(parameterList);
        assertEquals(" key1=value1 key2=value2 key3=value3", parameters);

    }

    @Test
    public void doubleToHumanReadable() {

        // Percent
        String result = StringUtils.doubleToHumanReadable(0.00, Metric.RESULT_TYPE_PERCENT);
        assertEquals("0%", result);

        result = StringUtils.doubleToHumanReadable(0.12, Metric.RESULT_TYPE_PERCENT);
        assertEquals("12%", result);

        result = StringUtils.doubleToHumanReadable(1.00, Metric.RESULT_TYPE_PERCENT);
        assertEquals("100%", result);

        // Int
        result = StringUtils.doubleToHumanReadable(0.00, Metric.RESULT_TYPE_INT);
        assertEquals("0", result);

        result = StringUtils.doubleToHumanReadable(0.12, Metric.RESULT_TYPE_INT);
        assertEquals("0", result);

        result = StringUtils.doubleToHumanReadable(1.00, Metric.RESULT_TYPE_INT);
        assertEquals("1", result);

        result = StringUtils.doubleToHumanReadable(123.45, Metric.RESULT_TYPE_INT);
        assertEquals("123", result);

        // Double
        result = StringUtils.doubleToHumanReadable(0.00, Metric.RESULT_TYPE_DOUBLE);
        assertEquals("0.0", result);

        result = StringUtils.doubleToHumanReadable(0.12, Metric.RESULT_TYPE_DOUBLE);
        assertEquals("0.12", result);

        result = StringUtils.doubleToHumanReadable(1.00, Metric.RESULT_TYPE_DOUBLE);
        assertEquals("1.0", result);

        result = StringUtils.doubleToHumanReadable(123.45, Metric.RESULT_TYPE_DOUBLE);
        assertEquals("123.45", result);

    }

    @Test
    public void escapeForMarkdown() {

        String result = StringUtils.escapeForMarkdown("This is a test.");
        assertEquals("This is a test.", result);

        result = StringUtils.escapeForMarkdown("This is a test with [square brackets].");
        assertEquals("This is a test with \\[square brackets\\].", result);

        result = StringUtils.escapeForMarkdown("This is a test with *stars*.");
        assertEquals("This is a test with \\*stars\\*.", result);

        result = StringUtils
                .escapeForMarkdown("This is a test with [square brackets] and *stars*.");
        assertEquals("This is a test with \\[square brackets\\] and \\*stars\\*.", result);

    }
}
