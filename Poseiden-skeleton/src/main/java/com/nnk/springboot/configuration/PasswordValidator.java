package com.nnk.springboot.configuration;

/**
 * Utility class for password validation.
 */
public class PasswordValidator {

    /**
     * Validates a password against specified criteria.
     *
     * <p>The password must meet the following conditions:</p>
     * <ul>
     *     <li>At least 8 characters long</li>
     *     <li>Contains at least one lowercase letter</li>
     *     <li>Contains at least one uppercase letter</li>
     *     <li>Contains at least one digit</li>
     *     <li>Contains at least one special character (e.g., @$!%*?&)</li>
     * </ul>
     *
     * @param password the password to be validated
     * @return {@code true} if the password meets all the criteria; {@code false} otherwise
     */
    public static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@$!%*?&].*");
    }
}
