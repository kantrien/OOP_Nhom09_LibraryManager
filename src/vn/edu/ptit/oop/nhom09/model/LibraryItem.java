package vn.edu.ptit.oop.nhom09.model;

import java.time.Year;


/**
 * STUB TẠM — chỉ để ItemFormDialog biên dịch được.
 * XÓA file này khi bạn phụ trách Model push LibraryItem thật.
 */
public abstract class LibraryItem {
    private final String id;
    private final String title;
    private final String author;
    private final int year;

    protected LibraryItem(String id, String title, String author, int year) {
    if (id == null || id.trim().isEmpty()) {
        throw new IllegalArgumentException("Mã tài liệu không được để trống");
    }

    if (title == null || title.trim().isEmpty()) {
        throw new IllegalArgumentException("Tên tài liệu không được để trống");
    }

    if (author == null || author.trim().isEmpty()) {
        throw new IllegalArgumentException("Tên tác giả không được để trống");
    }

    if (year <= 0 || year > Year.now().getValue()) {
        throw new IllegalArgumentException("Năm xuất bản không hợp lệ");
    }

    this.id = id.trim();
    this.title = title.trim();
    this.author = author.trim();
    this.year = year;
}

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }

    /** Tên loại hiển thị: "Sách" / "Tạp chí" / "Luận văn". */
    public abstract String getTypeName();

    public abstract int getLoanDays();
}
