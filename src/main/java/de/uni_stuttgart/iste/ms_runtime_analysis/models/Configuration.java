package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;

/**
 * Configuration model
 */
public class Configuration {

    private ArrayList<String> integrators;
    private ArrayList<String> exporters;
    private Map<String, Map<String, String>> integratorParams;
    private Map<String, Map<String, String>> exporterParams;
    private long endDate;
    private long startDate;

    /**
     * Creates new configuration object
     */
    public Configuration() {
        integrators = new ArrayList<>();
        exporters = new ArrayList<>();
        integratorParams = new HashMap<>();
        exporterParams = new HashMap<>();
    }

    /**
     * Adds integrator to the configuration
     * 
     * @param integrator integrator to be added
     */
    public void addIntegrator(String integrator) {
        integrators.add(integrator);
    }

    /**
     * Adds integrator parameter to the configuration
     * 
     * @param integratorName name of the integrator
     * @param param          parameter for the integrator, key-value pair
     */
    public void addIntegratorParam(String integratorName, Map<String, String> param) {

        if (integratorParams.containsKey(integratorName)) {
            integratorParams.get(integratorName).putAll(param);
        } else {
            integratorParams.put(integratorName, param);
        }

    }

    /**
     * Adds exporter to the configuration
     * 
     * @param exporter exporter to be added
     */
    public void addExporter(String exporter) {
        exporters.add(exporter);
    }

    /**
     * Adds exporter parameter to the configuration
     * 
     * @param exporterName name of the exporter
     * @param param        parameter for the exporter, key-value pair
     */
    public void addExporterParam(String exporterName, Map<String, String> param) {

        if (exporterParams.containsKey(exporterName)) {
            exporterParams.get(exporterName).putAll(param);
        } else {
            exporterParams.put(exporterName, param);
        }

    }

    /**
     * Sets the end date for the runtime data to be collected
     * 
     * @param endDate end date of runtime data to be collected
     */
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the start date for the runtime data to be collected
     * 
     * @param startDate start date of runtime data to be collected
     */
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    /**
     * Enables/Disables the debugging mode
     * 
     * @param enabled true to enable debug mode, else false
     */
    public void setDebugEnabled(boolean enabled) {
        Logger.setDebugEnabled(enabled);
    }

    /**
     * Lists integrators in the configuration
     * 
     * @return list of integrators
     */
    public String[] getIntegrators() {
        return integrators.toArray(new String[0]);
    }

    /**
     * Lists integrator parameters in the configuration
     * 
     * @param integratorName name of the integrator
     * @return list of integrator parameters
     */
    public Map<String, String> getIntegratorParams(String integratorName) {
        return integratorParams.get(integratorName);
    }

    /**
     * Lists exporters in the configuration
     * 
     * @return list of exporters
     */
    public String[] getExporters() {
        return exporters.toArray(new String[0]);
    }

    /**
     * Lists exporter parameters in the configuration
     * 
     * @param exporterName name of the exporter
     * @return list of exporter parameters
     */
    public Map<String, String> getExporterParams(String exporterName) {
        return exporterParams.get(exporterName);
    }

    /**
     * Gets the end date for the runtime data to be collected
     * 
     * @return end date of the runtime data to be collected
     */
    public long getEndDate() {
        return endDate;
    }

    /**
     * Gets the start date for the runtime data to be collected
     * 
     * @return start date of the runtime data to be collected
     */
    public long getStartDate() {
        return startDate;
    }

    /**
     * Checks if debugging mode is enabled
     * 
     * @return true if debugging mode is enabled, else false
     */
    public boolean debugEnabled() {
        return Logger.debugEnabled();
    }

}
