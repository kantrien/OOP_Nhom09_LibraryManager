package vn.edu.ptit.oop.nhom09.model;

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
        this.id = id;
        this.title = title;
        this.author = author;
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
