package vn.edu.ptit.oop.nhom09.exception;

// 1. Ngoại lệ khi không tìm thấy tài liệu
public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
