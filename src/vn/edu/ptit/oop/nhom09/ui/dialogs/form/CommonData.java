package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

/**
 * Gom các trường chung của mọi tài liệu, lấy từ phần trên của ItemFormDialog.
 * Dùng record (Java 16+): bất biến, tự sinh equals/hashCode/toString.
 */
public record CommonData(String id, String title, String author, int year) {}
