package vn.edu.ptit.oop.nhom09.model;

/** STUB TẠM — constructor khớp MockDataGenerator của Minh. */
public class Thesis extends LibraryItem {
    private final String supervisor;
    private final String university;

    public Thesis(String id, String title, String author, int year, String supervisor, String university) {
        super(id, title, author, year);
        this.supervisor = supervisor;
        this.university = university;
    }

    public String getSupervisor() { return supervisor; }
    public String getUniversity() { return university; }

    @Override public String getTypeName() { return "Luận văn"; }
    @Override public int getLoanDays() { return 3; }
}
