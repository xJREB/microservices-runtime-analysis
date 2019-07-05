package de.uni_stuttgart.iste.ms_runtime_analysis.interfaces;

import java.util.List;
import java.util.Map;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Metric;

/**
 * Interface to export the calculated metrics into a final format
 */
public interface ExporterInterface {

    /**
     * Export the calculated metrics into a final format
     * 
     * @param configuration application parameters
     * @param params        exporter parameters
     * @param metrics       calculated metrics
     * @return true if the export was successful, else false
     */
    Boolean exportResults(Configuration configuration, Map params, List<Metric> metrics);

}
