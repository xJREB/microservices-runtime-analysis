package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import java.util.Map;

/**
 * Metric model
 */
public class Metric {

    public static final int RESULT_ERROR = 0;
    public static final int RESULT_OK_SINGLE_RESULT = 1;
    public static final int RESULT_OK_MULTIPLE_RESULTS = 2;
    public static final int RESULT_OK_NESTED_RESULTS = 3;
    public static final int RESULT_NOT_PROCESSED_YET = 4;

    public static final int RESULT_TYPE_PERCENT = 0;
    public static final int RESULT_TYPE_INT = 1;
    public static final int RESULT_TYPE_DOUBLE = 2;

    private String longName;
    private String abbreviation;
    private String description;
    private String interpretation;
    private Integer resultStatus = RESULT_NOT_PROCESSED_YET;
    private int resultType;
    private String resultStatusMessage;
    private Double singleResult;
    private Map<String, Double> multipleResults;
    private Map<String, Map<String, Double>> nestedResults;
    private String[] resultDetails = null;

    /**
     * Creates new metric object
     * 
     * @param longName       name of the metric
     * @param abbreviation   abbreviation of the metric
     * @param description    description of the metric
     * @param interpretation interpretation of the metric
     * @param resultType     result representation to be used by the exporters
     */
    public Metric(String longName, String abbreviation, String description, String interpretation,
            int resultType) {
        this.longName = longName;
        this.abbreviation = abbreviation;
        this.description = description;
        this.interpretation = interpretation;
        this.resultType = resultType;
    }

    /**
     * Gets the name of the metric
     * 
     * @return name of the metric
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Sets the name of the metric
     * 
     * @param longName name of the metric
     */
    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * Gets the abbreviation of the metric
     * 
     * @return abbreviation of the metric
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the abbreviation of the metric
     * 
     * @param abbreviation abbreviation of the metric
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the description of the metric
     * 
     * @return description of the metric
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the metric
     * 
     * @param description description of the metric
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the result status code of the metric
     * 
     * @return result status code of the metric
     */
    public Integer getResultStatus() {
        return resultStatus;
    }

    /**
     * Gets the interpretation of the metric
     * 
     * @return interpretation of the metric
     */
    public String getInterpretation() {
        return interpretation;
    }

    /**
     * Sets the interpretation of the metric
     * 
     * @param interpretation interpretation of the metric
     */
    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    /**
     * Sets the result status code of the metric
     * 
     * @param resultStatus result status code of the metric
     */
    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * Gets the result representation type of the metric
     * 
     * @return result representation type of the metric
     */
    public int getResultType() {
        return resultType;
    }

    /**
     * Sets the result representation type of the metric
     * 
     * @param resultType result representation type of the metric
     */
    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    /**
     * Gets the result status message of the metric
     * 
     * @return result status message of the metric
     */
    public String getResultStatusMessage() {
        return resultStatusMessage;
    }

    /**
     * Sets the result status message of the metric
     * 
     * @param resultStatusMessage result status message of the metric
     */
    public void setResultStatusMessage(String resultStatusMessage) {
        this.resultStatusMessage = resultStatusMessage;
    }

    /**
     * Gets the single result value of the metric
     * 
     * @return single result value of the metric
     */
    public Double getSingleResult() {
        return singleResult;
    }

    /**
     * Sets the single result value of the metric
     * 
     * @param singleResult single result value of the metric
     */
    public void setSingleResult(Double singleResult) {
        this.singleResult = singleResult;
    }

    /**
     * Gets the result values of the metric
     * 
     * @return result values of the metric
     */
    public Map<String, Double> getMultipleResults() {
        return multipleResults;
    }

    /**
     * Sets the result values of the metric
     * 
     * @param multipleResults result values of the metric
     */
    public void setMultipleResults(Map<String, Double> multipleResults) {
        this.multipleResults = multipleResults;
    }

    /**
     * Gets the nested result values of the metric
     * 
     * @return nested result values of the metric
     */
    public Map<String, Map<String, Double>> getNestedResults() {
        return nestedResults;
    }

    /**
     * Sets the nested result values of the metric
     * 
     * @param nestedResults nested result values of the metric
     */
    public void setNestedResults(Map<String, Map<String, Double>> nestedResults) {
        this.nestedResults = nestedResults;
    }

    /**
     * Gets the result details of the metric
     * 
     * @return result details of the metric
     */
    public String[] getResultDetails() {
        return resultDetails;
    }

    /**
     * Sets the result details of the metric
     * 
     * @param resultDetails result details of the metric
     */
    public void setResultDetails(String[] resultDetails) {
        this.resultDetails = resultDetails;
    }

}
