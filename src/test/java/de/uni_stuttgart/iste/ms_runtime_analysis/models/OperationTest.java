package de.uni_stuttgart.iste.ms_runtime_analysis.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class OperationTest {

    @Test
    public void belongsToTest() {

        Service service = new Service("test-service");
        Operation operation = new Operation("test-parameter");

        assertNull(operation.belongsTo());
        operation.belongingTo(service);
        assertEquals(service, operation.belongsTo());

    }

    @Test
    public void parameterTests() {

        Operation operation = new Operation("test-parameter-1");

        assertEquals(1, operation.getParameters().length);

        operation.addParameter("test-parameter-2");

        assertEquals(2, operation.getParameters().length);

        operation.addParameter("test-parameter-3");
        operation.addParameter("test-parameter-4");

        assertEquals(4, operation.getParameters().length);

    }

    @Test
    public void callTests() {

        Operation operation = new Operation("test-parameter");

        Service caller1 = new Service("caller1");
        Service caller2 = new Service("caller2");
        Service caller3 = new Service("caller3");

        assertEquals(0, (long) operation.getCallCount(caller1));
        assertEquals(0, (long) operation.getCallCount(caller2));
        assertEquals(0, (long) operation.getCallCount(caller3));

        operation.increaseCallCount(caller1);
        operation.increaseCallCount(caller1);
        operation.increaseCallCount(caller1);
        operation.increaseCallCount(caller3);

        assertEquals(3, (long) operation.getCallCount(caller1));
        assertEquals(0, (long) operation.getCallCount(caller2));
        assertEquals(1, (long) operation.getCallCount(caller3));

        assertEquals(2, operation.getCalls().size());
        assertEquals(3, (long) operation.getCalls().get(caller1));
        assertNull(operation.getCalls().get(caller2));
        assertEquals(1, (long) operation.getCalls().get(caller3));

    }

    @Test
    public void responseForOperationTests() {

        Operation operation = new Operation("test-parameter");

        assertEquals(0, operation.getResponsesForOperation().length);

        operation.addResponseForOperation("rfo-1");

        assertEquals(1, operation.getResponsesForOperation().length);

        operation.addResponseForOperation("rfo-2");
        operation.addResponseForOperation("rfo-3");

        assertEquals(3, operation.getResponsesForOperation().length);

        assertEquals("rfo-1", operation.getResponsesForOperation()[0]);
        assertEquals("rfo-2", operation.getResponsesForOperation()[1]);
        assertEquals("rfo-3", operation.getResponsesForOperation()[2]);

    }

}
