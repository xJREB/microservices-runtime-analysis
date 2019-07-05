package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MathUtilsTest {

    @Test
    public void formatDouble() {

        double result = MathUtils.formatDouble(0.0000);
        assertEquals(0.00, result, 0.0);

        result = MathUtils.formatDouble(1.0000);
        assertEquals(1.00, result, 0.0);

        result = MathUtils.formatDouble(0.123456);
        assertEquals(0.12, result, 0.0);

        result = MathUtils.formatDouble(1234.5);
        assertEquals(1234.50, result, 0.0);

        result = MathUtils.formatDouble(1234.5678);
        assertEquals(1234.57, result, 0.0);

        result = MathUtils.formatDouble(1234.56);
        assertEquals(1234.56, result, 0.0);

    }

    @Test
    public void factorial() {

        long result = MathUtils.factorial(-10);
        assertEquals(1, result);

        result = MathUtils.factorial(-1);
        assertEquals(1, result);

        result = MathUtils.factorial(0);
        assertEquals(1, result);

        result = MathUtils.factorial(1);
        assertEquals(1, result);

        result = MathUtils.factorial(2);
        assertEquals(2, result);

        result = MathUtils.factorial(3);
        assertEquals(6, result);

        result = MathUtils.factorial(4);
        assertEquals(24, result);

        result = MathUtils.factorial(5);
        assertEquals(120, result);

        result = MathUtils.factorial(10);
        assertEquals(3628800, result);

        result = MathUtils.factorial(15);
        assertEquals(1307674368000L, result);

    }
}
