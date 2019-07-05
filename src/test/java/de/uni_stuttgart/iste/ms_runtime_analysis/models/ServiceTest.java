package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ServiceTest {

    @Test
    public void nameTests() {

        Service service = new Service("");
        assertEquals("", service.getName());
        service.setName("test-service");
        assertEquals("test-service", service.getName());

    }

    @Test
    public void operationTests() {

        Service service = new Service("test-service");

        // Check zero operations
        assertEquals(0, service.getOperations().length);

        // Check one operation
        service.addOperation(new Operation("test-parameter-1"));

        assertEquals(1, service.getOperations().length);

        // Check multiple operations
        service.addOperation(new Operation("test-parameter-2"));
        service.addOperation(new Operation("test-parameter-3"));

        assertEquals(3, service.getOperations().length);

        // Search operation by operation object
        Operation operation = service.findOperation(new Operation(""));
        assertNull(operation);

        operation = service.findOperation(new Operation("test-parameter-1"));
        assertNotNull(operation);
        assertEquals("test-parameter-1", operation.getParameters()[0]);

        // Search operation by parameter string
        operation = service.findOperation("");
        assertNull(operation);

        operation = service.findOperation("test-parameter-1");
        assertNotNull(operation);
        assertEquals("test-parameter-1", operation.getParameters()[0]);

        // Check operation exists
        assertFalse(service.operationExists(new Operation("")));
        assertTrue(service.operationExists(new Operation("test-parameter-2")));

        // Check add operation if not exists
        service = new Service("test-service");
        Operation newOperation = new Operation("add-operation-test");

        assertEquals(0, service.getOperations().length);

        service.addOperationIfNotExists(newOperation);

        assertEquals(1, service.getOperations().length);

        service.addOperationIfNotExists(newOperation);
        service.addOperationIfNotExists(newOperation);

        assertEquals(1, service.getOperations().length);

    }

}
