package vn.edu.ptit.oop.nhom09.exception;

// Ngoại lệ khi mượn/trả quá hạn hoặc vi phạm quy định mượn sách
public class OverdueException extends Exception {
    public OverdueException(String message) {
        super(message);
    }
}