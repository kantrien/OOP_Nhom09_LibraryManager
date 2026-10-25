package vn.edu.ptit.oop.nhom09.model;

/** STUB TẠM — constructor khớp MockDataGenerator của Minh. */
public class Book extends LibraryItem {
    private final String isbn;
    private final int pages;

    public Book(String id, String title, String author, int year, String isbn, int pages) {
        super(id, title, author, year);
        this.isbn = isbn;
        this.pages = pages;
    }

    public String getIsbn() { return isbn; }
    public int getPages() { return pages; }

    @Override public String getTypeName() { return "Sách"; }
    @Override public int getLoanDays() { return 14; }
}
