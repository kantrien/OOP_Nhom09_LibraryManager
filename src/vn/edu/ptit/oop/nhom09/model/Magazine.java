package vn.edu.ptit.oop.nhom09.model;

public class Magazine extends LibraryItem {

    private final int issueNumber;

    public Magazine(String id, String title, String author,
                    int year, int issueNumber) {
        super(id, title, author, year);

        if (issueNumber <= 0) {
            throw new IllegalArgumentException(
                "Số kỳ tạp chí phải lớn hơn 0"
            );
        }

        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String getTypeName() {
        return "Tạp chí";
    }

    @Override
    public int getLoanDays() {
        return 7;
    }
}