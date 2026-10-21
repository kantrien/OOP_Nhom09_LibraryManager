package vn.edu.ptit.oop.nhom09.util;

import java.time.Year;

/**
 * STUB TẠM — bạn phụ trách ValidationUtil sẽ viết bản thật.
 * Lưu ý cho bản thật: ISBN mẫu có dấu '-', phải bỏ trước khi đếm.
 */
public final class ValidationUtil {
    private ValidationUtil() {}

    public static boolean isValidIsbn(String isbn) {
        if (isbn == null) return false;
        String digits = isbn.replaceAll("[-\\s]", "");
        return digits.matches("\\d{10}|\\d{13}");
    }

    public static boolean isValidYear(int year) {
        return year >= 1900 && year <= Year.now().getValue();
    }
}
