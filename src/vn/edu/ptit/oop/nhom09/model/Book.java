
package vn.edu.ptit.oop.nhom09.model;

/**
 * Lớp Book đại diện cho sách trong thư viện.
 * Kế thừa các thuộc tính chung từ LibraryItem.
 */
public class Book extends LibraryItem {

    private final String isbn;
    private final int pages;

    public Book(String id, String title, String author, int year,
                String isbn, int pages) {

        // Khởi tạo các thuộc tính chung của tài liệu
        super(id, title, author, year);

        // Kiểm tra ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "ISBN không được để trống");
        }

        // Kiểm tra số trang
        if (pages <= 0) {
            throw new IllegalArgumentException(
                    "Số trang phải lớn hơn 0");
        }

        this.isbn = isbn.trim();
        this.pages = pages;
    }

    // Lấy ISBN
    public String getIsbn() {
        return isbn;
    }

    // Lấy số trang
    public int getPages() {
        return pages;
    }

    // Trả về tên loại tài liệu
    @Override
    public String getTypeName() {
        return "Sách";
    }

    // Số ngày được phép mượn
    @Override
    public int getLoanDays() {
        return 14;
    }
}
