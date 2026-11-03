package vn.edu.ptit.oop.nhom09.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.model.LoanRecord;

public class LoanService {

    private final List<LoanRecord> loans = new ArrayList<>();

    public LoanRecord borrowItem(LibraryItem item, String borrowerId,
                                 String borrowerName, LocalDate loanDate) {
        Objects.requireNonNull(item, "Tài liệu không được để trống");
        Objects.requireNonNull(loanDate, "Ngày mượn không được để trống");

        if (isItemBorrowed(item.getId())) {
            throw new IllegalStateException("Tài liệu đang được người khác mượn");
        }
        if (item.getLoanDays() <= 0) {
            throw new IllegalArgumentException("Thời hạn mượn phải lớn hơn 0 ngày");
        }

        LoanRecord loan = new LoanRecord(
                item.getId(),
                borrowerId,
                borrowerName,
                loanDate,
                loanDate.plusDays(item.getLoanDays()));
        loans.add(loan);
        return loan;
    }

    public boolean isItemBorrowed(String itemId) {
        requireText(itemId, "Mã tài liệu");
        return loans.stream()
                .anyMatch(loan -> loan.getItemId().equals(itemId) && !loan.isReturned());
    }

    public boolean isOverdue(String itemId, LocalDate date) {
        Objects.requireNonNull(date, "Ngày kiểm tra không được để trống");
        LoanRecord loan = findActiveLoan(itemId);
        return loan != null && isOverdue(loan, date);
    }

    public boolean isOverdue(LoanRecord loan, LocalDate date) {
        Objects.requireNonNull(loan, "Thông tin mượn không được để trống");
        Objects.requireNonNull(date, "Ngày kiểm tra không được để trống");
        return !loan.isReturned() && date.isAfter(loan.getDueDate());
    }


public void returnItem(String itemId) throws vn.edu.ptit.oop.nhom09.exception.OverdueException {
    for (LoanRecord record : loans) {
        if (record.getItemId().equals(itemId) && !record.isReturned()) {
        LoanRecord updatedRecord = record.markReturned(java.time.LocalDate.now());

        int index = loans.indexOf(record);
        if (index != -1) {
            loans.set(index, updatedRecord);
}
            // Lấy ngày trả thực tế (hôm nay) so sánh với ngày phải trả (dueDate)
            java.time.LocalDate today = java.time.LocalDate.now();
            if (today.isAfter(record.getDueDate())) {
             
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), today);
                long fineAmount = daysLate * 5000; 
                
                throw new vn.edu.ptit.oop.nhom09.exception.OverdueException(
                    "Tài liệu đã trễ hạn " + daysLate + " ngày.\nSố tiền phạt: " + fineAmount + " VNĐ"
                );
            }
            break;
        }
    }
}

    public List<LoanRecord> getAllLoans() {
        return List.copyOf(loans);
    }

    private LoanRecord findActiveLoan(String itemId) {
        requireText(itemId, "Mã tài liệu");
        return loans.stream()
                .filter(loan -> loan.getItemId().equals(itemId) && !loan.isReturned())
                .findFirst()
                .orElse(null);
    }
private static void requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " không được để trống");
        }
    }
}