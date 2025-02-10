package utils;

import java.util.regex.Pattern;

public class InputValidator {

    // Validate non-empty string input (e.g., product name, category name)
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validate positive integer (e.g., quantity, IDs)
    public static boolean isValidPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate positive decimal (e.g., product price)
    public static boolean isValidPositiveDecimal(String input) {
        try {
            double value = Double.parseDouble(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate email format (for customer contact info)
    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    // Validate order status (Allowed: Pending, Processing, Shipped, Delivered, Canceled)
    public static boolean isValidOrderStatus(String status) {
        return status.matches("(?i)Pending|Processing|Shipped|Delivered|Canceled");
    }
}

