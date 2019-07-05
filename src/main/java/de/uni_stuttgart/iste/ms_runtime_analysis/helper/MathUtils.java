package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import java.util.Locale;

/**
 * Defines helper functions for mathematical purposes
 */
public class MathUtils {

    private MathUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Limits a double value to two fractional digits
     * 
     * @param value double to be limited
     * @return limited double
     */
    public static Double formatDouble(double value) {
        return Double.parseDouble(String.format(Locale.ENGLISH, "%1.2f", value));
    }

    /**
     * Calculates the factorial of a number
     * 
     * @param number number of which to calculate the factorial
     * @return calculated factorial
     */
    public static long factorial(long number) {
        if (number <= 1)
            return 1;
        else
            return number * factorial(number - 1);
    }

}
