package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Operation model
 */
public class Operation {

    private String id;
    private Service belongsTo = null;
    private ArrayList<String> parameters;
    private Map<Service, Long> calls;
    private ArrayList<String> responsesForOperation;

    /**
     * Creates new operation object
     * 
     * @param parameters parameters of the operation, comma-separated
     */
    public Operation(String parameters) {
        this.id = UUID.randomUUID().toString();
        this.parameters = new ArrayList<>(Arrays.asList(parameters.split(",")));
        this.calls = new HashMap<>();
        this.responsesForOperation = new ArrayList<>();
    }

    /**
     * Set the service this operation belongs to
     * 
     * @param service service this operation belongs to
     */
    public void belongingTo(Service service) {
        belongsTo = service;
    }

    /**
     * Get the service this operation belongs to
     * 
     * @return service this operation belongs to
     */
    public Service belongsTo() {
        return belongsTo;
    }

    /**
     * Adds a parameter to this operation
     * 
     * @param parameter new parameter for this operation
     */
    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    /**
     * Gets the parameters of this operation
     * 
     * @return parameters of this operation
     */
    public String[] getParameters() {
        return parameters.toArray(new String[0]);
    }

    /**
     * Increase the call count to this operation by one
     * 
     * @param from caller of this operation
     */
    public void increaseCallCount(Service from) {
        calls.put(from, calls.containsKey(from) ? (calls.get(from) + 1) : 1);
    }

    /**
     * Gets the call count for this operation
     * 
     * @param from caller from which to get the call count
     * @return call count for this caller
     */
    public Long getCallCount(Service from) {
        return calls.getOrDefault(from, 0L);
    }

    /**
     * Get all calls to this operation
     * 
     * @return list with caller to call count mapping
     */
    public Map<Service, Long> getCalls() {
        return calls;
    }

    /**
     * Adds a response to the response for operation list
     * 
     * @param response new response for this operation
     */
    public void addResponseForOperation(String response) {
        if (!responsesForOperation.contains(response)) {
            responsesForOperation.add(response);
        }
    }

    /**
     * Gets the responses for this operation
     * 
     * @return responses for this operation
     */
    public String[] getResponsesForOperation() {
        return responsesForOperation.toArray(new String[0]);
    }

    /**
     * Converts this object to a string
     * 
     * @return string representation of this object
     */
    public String toString() {
        return parameters.toString();
    }

    /**
     * Gets the hashcode of this object
     * 
     * @return hashcode of this object
     */
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Checks if this object equals the given object
     * 
     * @param o object to compare this object to
     * @return true if objects equal, else false
     */
    public boolean equals(Object o) {
        return (o instanceof Operation) && (id.equals(((Operation) o).id));
    }

}
