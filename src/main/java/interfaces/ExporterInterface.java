package interfaces;

import java.util.ArrayList;
import java.util.Map;
import models.Configuration;
import models.Metric;

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
    Boolean exportResults(Configuration configuration, Map params, ArrayList<Metric> metrics);

}
