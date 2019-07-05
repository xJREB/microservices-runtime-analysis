package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Service model
 */
public class Service {

    private String id;
    private String name;
    private ArrayList<Operation> operations;

    /**
     * Creates new service object
     * 
     * @param name name of the service
     */
    public Service(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.operations = new ArrayList<>();
    }

    /**
     * Sets the name of the service
     * 
     * @param name name of the service
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the service
     * 
     * @return name of the service
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an operation to the service
     * 
     * @param operation operation to be added to the service
     */
    public void addOperation(Operation operation) {
        operation.belongingTo(this);
        operations.add(operation);
    }

    /**
     * Adds an operation to the service if it doesn't already exist
     * 
     * @param operation operation to be added to the service
     */
    public void addOperationIfNotExists(Operation operation) {

        if (!operationExists(operation)) {
            operations.add(operation);
        }

    }

    /**
     * Finds an operation by its parameters
     * 
     * @param operation operation containing parameters matching the searched operation
     * @return found operation or null
     */
    public Operation findOperation(Operation operation) {

        Operation[] currentOperations = getOperations();
        for (Operation currentOperation : currentOperations) {

            if (currentOperation.getParameters().length != operation.getParameters().length) {
                continue;
            }

            if (Arrays.asList(currentOperation.getParameters())
                    .containsAll(Arrays.asList(operation.getParameters()))
                    && Arrays.asList(operation.getParameters())
                            .containsAll(Arrays.asList(currentOperation.getParameters()))) {
                return currentOperation;
            }

        }

        return null;
    }

    /**
     * Finds an operation by its parameters
     * 
     * @param parameters string array containing parameters matching the searched operation
     * @return found operation or null
     */
    public Operation findOperation(String parameters) {
        Operation operation = new Operation(parameters);
        return findOperation(operation);
    }

    /**
     * Checks if an operation exists in this service
     * 
     * @param operation operation containing parameters matching the searched operation
     * @return true if the operation exists in this service, else false
     */
    public boolean operationExists(Operation operation) {
        return (findOperation(operation) != null);
    }

    /**
     * Get all operations of this service
     * 
     * @return list of all operations of this service
     */
    public Operation[] getOperations() {
        return operations.toArray(new Operation[0]);
    }

    /**
     * Converts this object to a string
     * 
     * @return string representation of this object
     */
    public String toString() {
        return name;
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
        return (o instanceof Service) && (id.equals(((Service) o).id));
    }

}
