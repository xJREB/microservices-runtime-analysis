package de.uni_stuttgart.iste.ms_runtime_analysis.interfaces;

import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Interface to integrate the runtime data into the canonical format
 */
public interface IntegratorInterface {

    /**
     * Integrate the runtime data into the canonical format
     * 
     * @param configuration application configuration
     * @param params        integrator parameters
     * @param graph         current runtime data to be extended
     * @return new runtime data
     */
    DefaultDirectedGraph<Service, DefaultEdge> integrateRuntimeData(Configuration configuration,
            Map params, DefaultDirectedGraph<Service, DefaultEdge> graph);

}
