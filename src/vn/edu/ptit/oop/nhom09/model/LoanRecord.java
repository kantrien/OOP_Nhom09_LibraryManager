package vn.edu.ptit.oop.nhom09.model;

import java.time.LocalDate;
import java.util.Objects;

public class LoanRecord {

    private final String itemId;
    private final String borrowerId;
    private final String borrowerName;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private final LocalDate returnedDate;

    public LoanRecord(String itemId, String borrowerId, String borrowerName,
                      LocalDate loanDate, LocalDate dueDate) {
        this(itemId, borrowerId, borrowerName, loanDate, dueDate, null);
    }

    private LoanRecord(String itemId, String borrowerId, String borrowerName,
                       LocalDate loanDate, LocalDate dueDate,
                       LocalDate returnedDate) {
        this.itemId = requireText(itemId, "Mã tài liệu");
        this.borrowerId = requireText(borrowerId, "Mã người mượn");
        this.borrowerName = requireText(borrowerName, "Tên người mượn");
        this.loanDate = Objects.requireNonNull(loanDate, "Ngày mượn không được để trống");
        this.dueDate = Objects.requireNonNull(dueDate, "Hạn trả không được để trống");
        if (dueDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Hạn trả không được trước ngày mượn");
        }
        if (returnedDate != null && returnedDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Ngày trả không được trước ngày mượn");
        }
        this.returnedDate = returnedDate;
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " không được để trống");
        }
        return value.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public boolean isReturned() {
        return returnedDate != null;
    }

    public LoanRecord markReturned(LocalDate date) {
        Objects.requireNonNull(date, "Ngày trả không được để trống");
        if (isReturned()) {
            throw new IllegalStateException("Tài liệu đã được trả");
        }
        if (date.isBefore(loanDate)) {
            throw new IllegalArgumentException("Ngày trả không được trước ngày mượn");
        }
        return new LoanRecord(itemId, borrowerId, borrowerName, loanDate, dueDate, date);
    }
}