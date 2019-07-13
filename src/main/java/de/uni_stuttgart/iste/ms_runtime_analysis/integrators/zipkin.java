package de.uni_stuttgart.iste.ms_runtime_analysis.integrators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.Logger;
import de.uni_stuttgart.iste.ms_runtime_analysis.helper.NetworkUtils;
import de.uni_stuttgart.iste.ms_runtime_analysis.interfaces.IntegratorInterface;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Configuration;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Operation;
import de.uni_stuttgart.iste.ms_runtime_analysis.models.Service;

/**
 * Integrates runtime data from zipkin
 */
public class zipkin implements IntegratorInterface {

    /**
     * Integrate runtime data from zipkin
     * 
     * @param configuration application configuration
     * @param params        integrator parameters
     * @param graph         current runtime data to be extended
     * @return new runtime data
     */
    public DefaultDirectedGraph<Service, DefaultEdge> integrateRuntimeData(
            Configuration configuration, Map params,
            DefaultDirectedGraph<Service, DefaultEdge> graph) {

        // Check if required params are set
        if ((params == null) || (!params.containsKey("url"))) {
            Logger.err(
                    "         Error: Missing required parameter \"url\", continuing with next integrator");
            return graph;
        }

        long endDate = configuration.getEndDate();
        long startDate = configuration.getStartDate();
        long lookback = endDate - startDate;

        // Fetch the services list
        String url = params.get("url") + "/api/v2/services";
        String fullJSON;
        try {
            fullJSON = NetworkUtils.fetchDocument(url);
        } catch (IOException e) {
            Logger.err("         Error: Could not connect to zipkin at " + url
                    + ", continuing with next integrator");
            return graph;
        }

        // Parse services list
        ArrayList<String> servicesList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {

            JSONArray jsonEntries = (JSONArray) parser.parse(fullJSON);

            for (Object currentJSONEntry : jsonEntries) {
                String jsonEntry = (String) currentJSONEntry;
                servicesList.add(jsonEntry);
            }

        } catch (ParseException e) {
            Logger.err(
                    "         Error: Cannot parse services list, continuing with next integrator: "
                            + e.getMessage());
            return graph;
        }

        // Iterate through the traces of each found service
        Map<String, Service> serviceMap = new HashMap<>();
        HashSet<String> processedCallSignatures = new HashSet<>();

        for (String currentService : servicesList) {

            // Fetch the traces for this service
            url = params.get("url") + "/api/v2/traces?serviceName=" + currentService + "&endTs="
                    + endDate + "&lookback=" + lookback + "&limit=1000000000";
            try {
                fullJSON = NetworkUtils.fetchDocument(url);
            } catch (IOException e) {
                Logger.err("         Error: Could not connect to zipkin at " + url
                        + ", continuing with next integrator");
                return graph;
            }

            // Parse the trace list
            try {

                JSONArray outerJSONArray = (JSONArray) parser.parse(fullJSON);

                for (Object innerJSONObject : outerJSONArray) {

                    JSONArray innerJSONArray = (JSONArray) parser.parse(innerJSONObject.toString());

                    Map<String, ArrayList<Operation>> idToOperations = new HashMap<>();
                    Map<Operation, ArrayList<Long>> operationToTimestamp = new HashMap<>();

                    // Search for operations
                    for (Object currentJSONEntry : innerJSONArray) {

                        JSONObject jsonEntry = (JSONObject) currentJSONEntry;

                        // Skip all non-server entries
                        if (!jsonEntry.get("kind").toString().equals("SERVER")) {
                            continue;
                        }

                        // Skip server stop traces
                        if (jsonEntry.containsKey("annotations") && jsonEntry.get("annotations")
                                .toString().contains("value\":\"ss")) {
                            continue;
                        }

                        // Get local endpoint
                        JSONObject localEndpoint = (JSONObject) parser
                                .parse(jsonEntry.get("localEndpoint").toString());
                        String localEndpointName = localEndpoint.get("serviceName").toString();

                        // Create service node if not exists
                        if (!serviceMap.containsKey(localEndpointName)) {
                            Service vertex = new Service(localEndpointName);
                            serviceMap.put(localEndpointName, vertex);
                            graph.addVertex(vertex);
                        }

                        // Add operation to service operations list if it is a server call
                        Operation newOperation = new Operation(jsonEntry.get("name").toString());
                        Operation operation =
                                serviceMap.get(localEndpointName).findOperation(newOperation);

                        if (operation == null) {
                            serviceMap.get(localEndpointName).addOperation(newOperation);
                            operation = newOperation;
                        }

                        // Save id to operation mapping for caller mapping
                        String id = jsonEntry.get("id").toString();

                        if (!idToOperations.containsKey(id)) {
                            idToOperations.put(id, new ArrayList<>());
                        }

                        idToOperations.get(id).add(operation);

                        // Save timestamp of operation for response for operation mapping
                        Long timestamp = Long.valueOf(jsonEntry.get("timestamp").toString());

                        if (!operationToTimestamp.containsKey(operation)) {
                            operationToTimestamp.put(operation, new ArrayList<>());
                        }

                        operationToTimestamp.get(operation).add(timestamp);

                        Logger.debug("         " + localEndpointName + " ("
                                + jsonEntry.get("id").toString() + ") <-- "
                                + jsonEntry.get("name").toString());
                    }

                    // Search for callers of operations
                    for (Object currentJSONEntry : innerJSONArray) {

                        JSONObject jsonEntry = (JSONObject) currentJSONEntry;

                        // Skip all non-client entries
                        if (!jsonEntry.get("kind").toString().equals("CLIENT")) {
                            continue;
                        }

                        // Skip client return traces
                        if (jsonEntry.containsKey("annotations") && jsonEntry.get("annotations")
                                .toString().contains("value\":\"cr")) {
                            continue;
                        }

                        // Get local endpoint
                        JSONObject localEndpoint = (JSONObject) parser
                                .parse(jsonEntry.get("localEndpoint").toString());
                        String localEndpointName = localEndpoint.get("serviceName").toString();

                        // Check if request has already been logged
                        String id = jsonEntry.get("id").toString();
                        String traceId = jsonEntry.get("traceId").toString();
                        String timestamp = jsonEntry.get("timestamp").toString();
                        String name = jsonEntry.get("name").toString();
                        String callSignature = String.valueOf((traceId + "-" + id + "-"
                                + localEndpointName + "-" + timestamp + "-" + name).hashCode());

                        if (processedCallSignatures.contains(callSignature)) {
                            continue;
                        }

                        // Check if this call is in response to another call, exclude seneca data
                        // persistence operations
                        Long clientTimestamp = Long.valueOf(jsonEntry.get("timestamp").toString());

                        if ((!name.contains("role:entity"))
                                && (jsonEntry.containsKey("parentId"))) {

                            String parentId = jsonEntry.get("parentId").toString();

                            if (idToOperations.containsKey(parentId)) {

                                Operation bestOperation = null;
                                Long bestTimestamp = 0L;

                                for (Operation operation : idToOperations.get(parentId)) {

                                    if (!operationToTimestamp.containsKey(operation)) {
                                        continue;
                                    }

                                    for (Long serverTimestamp : operationToTimestamp
                                            .get(operation)) {

                                        if ((serverTimestamp < clientTimestamp)
                                                && (serverTimestamp > bestTimestamp)) {
                                            bestOperation = operation;
                                            bestTimestamp = serverTimestamp;
                                        }

                                    }

                                }

                                if (bestOperation != null) {
                                    Logger.debug("         Found response for operation from "
                                            + bestOperation.belongsTo().getName() + ": "
                                            + Arrays.toString(bestOperation.getParameters())
                                            + " to " + name + " at " + clientTimestamp
                                            + " in trace " + traceId);
                                    bestOperation.addResponseForOperation(name);
                                }

                            }

                        }

                        Logger.debug("         " + localEndpointName + " ("
                                + jsonEntry.get("id").toString() + ") ---> "
                                + jsonEntry.get("name").toString());

                        // Add calls to operations
                        if (idToOperations.get(id) != null) {

                            for (Operation currentOperation : idToOperations.get(id)) {

                                // Skip calls to self
                                if (localEndpointName
                                        .equals(currentOperation.belongsTo().getName())) {
                                    continue;
                                }

                                // Create service node if not exists
                                if (!serviceMap.containsKey(localEndpointName)) {
                                    Service vertex = new Service(localEndpointName);
                                    serviceMap.put(localEndpointName, vertex);
                                    graph.addVertex(vertex);
                                }

                                // Create directed edge between services
                                if (!graph.containsEdge(serviceMap.get(localEndpointName),
                                        currentOperation.belongsTo())) {
                                    graph.addEdge(serviceMap.get(localEndpointName),
                                            currentOperation.belongsTo());
                                }

                                currentOperation
                                        .increaseCallCount(serviceMap.get(localEndpointName));
                                processedCallSignatures.add(callSignature);

                                Logger.debug("         Found call for "
                                        + Arrays.toString(currentOperation.getParameters())
                                        + " from " + localEndpointName);
                            }

                        }

                    }

                    Logger.debug("         " + innerJSONArray.toString());
                    Logger.debug(
                            "         ###############################################################################################");
                }

            } catch (ParseException e) {
                Logger.err(
                        "         Error: Cannot parse trace list, continuing with next integrator: "
                                + e.getMessage());
                return graph;
            }

        }

        // List found services, operations and calls
        for (Service service : serviceMap.values()) {

            Logger.debug("         " + service.getName());

            for (Operation operation : service.getOperations()) {
                Logger.debug("            " + Arrays.toString(operation.getParameters())
                        + " --> Calls from " + operation.getCalls().toString() + ", RFOs: "
                        + Arrays.toString(operation.getResponsesForOperation()));
            }

        }

        int operations = 0;
        int calls = 0;

        for (Service service : serviceMap.values()) {

            operations += service.getOperations().length;

            for (Operation operation : service.getOperations()) {

                for (Long callCount : operation.getCalls().values()) {
                    calls += callCount;
                }

            }

        }

        Logger.out("         Number of found services: " + serviceMap.size());
        Logger.out("         Number of found operations: " + operations);
        Logger.out("         Number of found operation calls: " + calls);

        return graph;
    }

}
