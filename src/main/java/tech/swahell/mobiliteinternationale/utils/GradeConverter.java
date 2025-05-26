package tech.swahell.mobiliteinternationale.utils;

import java.util.Locale;
import java.util.Random;

/**
 * Utility class to convert foreign grades to ENSIAS grading scale.
 */
public class GradeConverter {

    private static final Random random = new Random();

    /**
     * Converts a foreign grade to ENSIAS equivalent based on the country.
     *
     * @param foreignGrade original grade from the foreign university
     * @param country      name of the partner country (case insensitive)
     * @return converted grade on a scale of 0 to 20
     */
    public static double convert(double foreignGrade, String country) {
        if (country == null) {
            return foreignGrade; // fallback
        }

        country = country.trim().toLowerCase(Locale.ROOT);

        switch (country) {
            case "france":
                return Math.min(round(foreignGrade * 1.2), 20);

            case "usa":
            case "canada":
                return Math.min(round(foreignGrade * 0.24), 20);

            case "italy":
            case "italie":
                return Math.min(round(foreignGrade * 0.6667), 20);

            case "finland":
                return convertFinnish(foreignGrade);

            case "germany":
            case "allemagne":
                return convertGerman(foreignGrade);

            default:
                // Unknown country: no conversion
                return foreignGrade;
        }
    }

    /**
     * Convert Finnish grades (scale 0–5) to ENSIAS 0–20.
     */
    private static double convertFinnish(double score) {
        if (score == 5) {
            return 20;
        } else if (score == 4) {
            return getRandomBetween(19.2, 20);
        } else if (score == 3) {
            return getRandomBetween(16.8, 18);
        } else if (score == 2) {
            return getRandomBetween(14.4, 15.6);
        } else if (score == 1) {
            return 12;
        } else {
            return getRandomBetween(0, 11.9); // fail or invalid
        }
    }

    /**
     * Convert German grade (scale 1–4.0, where 1 is best) to ENSIAS scale.
     */
    private static double convertGerman(double grade) {
        if (grade >= 4.0) return 11.9; // fail in ENSIAS
        double ensiasGrade = (-8.0 / 3.0) * grade + (44.0 / 3.0);
        return Math.min(round(ensiasGrade), 20);
    }

    /**
     * Generate a random grade between two values, rounded to 2 decimal places.
     */
    private static double getRandomBetween(double min, double max) {
        double randomValue = min + (max - min) * random.nextDouble();
        return round(randomValue);
    }

    /**
     * Rounds a number to 2 decimal places.
     */
    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
