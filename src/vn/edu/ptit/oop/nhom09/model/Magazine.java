package vn.edu.ptit.oop.nhom09.model;

/** STUB TẠM — constructor khớp MockDataGenerator của Minh. */
public class Magazine extends LibraryItem {
    private final int issueNumber;

    public Magazine(String id, String title, String author, int year, int issueNumber) {
        super(id, title, author, year);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() { return issueNumber; }

    @Override public String getTypeName() { return "Tạp chí"; }
    @Override public int getLoanDays() { return 7; }
}
