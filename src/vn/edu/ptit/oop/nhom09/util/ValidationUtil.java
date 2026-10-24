
package vn.edu.ptit.oop.nhom09.util;

import java.time.Year;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static String normalizeISBN(String isbn) {
        if (isbn == null) {
            return "";
        }
        return isbn.replaceAll("[\\s-]", "").toUpperCase();
    }

    public static boolean isValidISBN10(String isbn) {
        String value = normalizeISBN(isbn);

        if (!value.matches("[0-9]{9}[0-9X]")) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < 10; i++) {
            char c = value.charAt(i);
            int digit = (c == 'X') ? 10 : c - '0';
            sum += digit * (10 - i);
        }

        return sum % 11 == 0;
    }

    public static boolean isValidISBN13(String isbn) {
        String value = normalizeISBN(isbn);

        if (!value.matches("[0-9]{13}")) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < 12; i++) {
            int digit = value.charAt(i) - '0';
            sum += digit * (i % 2 == 0 ? 1 : 3);
        }

        int checkDigit = (10 - sum % 10) % 10;
        return checkDigit == value.charAt(12) - '0';
    }

    public static boolean isValidISBN(String isbn) {
        return isValidISBN10(isbn) || isValidISBN13(isbn);
    }

    public static boolean isValidPublicationYear(int year) {
        return year > 0 && year <= Year.now().getValue();
    }

    public static boolean isValidPublicationYear(String year) {
        if (year == null || year.trim().isEmpty()) {
            return false;
        }

        try {
            return isValidPublicationYear(
                Integer.parseInt(year.trim())
            );
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
