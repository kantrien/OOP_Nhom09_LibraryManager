
package vn.edu.ptit.oop.nhom09.model;

/**
 * Lớp Thesis đại diện cho luận văn trong thư viện.
 * Kế thừa các thuộc tính chung từ LibraryItem.
 */
public class Thesis extends LibraryItem {

    private final String supervisor;
    private final String university;

    public Thesis(
            String id,
            String title,
            String author,
            int year,
            String supervisor,
            String university) {

        super(id, title, author, year);

        if (supervisor == null || supervisor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Tên giảng viên hướng dẫn không được để trống");
        }

        if (university == null || university.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Tên trường đại học không được để trống");
        }

        this.supervisor = supervisor.trim();
        this.university = university.trim();
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getUniversity() {
        return university;
    }

    @Override
    public String getTypeName() {
        return "Luận văn";
    }

    @Override
    public int getLoanDays() {
        return 3;
    }
}
