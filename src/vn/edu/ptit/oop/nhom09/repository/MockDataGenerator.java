package vn.edu.ptit.oop.nhom09.repository;

import vn.edu.ptit.oop.nhom09.model.Book;
import vn.edu.ptit.oop.nhom09.model.Magazine;
import vn.edu.ptit.oop.nhom09.model.Thesis;

public class MockDataGenerator {

    public static void generateSampleData(LibraryRepository repo) {
       
        repo.add(new Book("B001", "Lập trình Java Nâng cao", "Nguyễn Văn A", 2021, "978-604-0-12345-1", 450));
        repo.add(new Book("B002", "Cấu trúc dữ liệu & Giải thuật", "Trần Thị B", 2019, "978-604-0-67890-2", 320));
        repo.add(new Book("B003", "Thiết kế Mẫu (Design Patterns)", "Erich Gamma", 2020, "978-020-1-63361-0", 395));
        repo.add(new Book("B004", "Clean Code", "Robert C. Martin", 2018, "978-013-2-35088-4", 464));
        repo.add(new Book("B005", "Nhập môn Trí tuệ Nhân tạo", "Phạm Văn C", 2022, "978-604-0-11223-3", 280));
        repo.add(new Book("B006", "Hệ quản trị Cơ sở Dữ liệu", "Lê Thị D", 2020, "978-604-0-44556-4", 510));

       
        repo.add(new Magazine("M001", "Tạp chí Tin học & Điều khiển", "Hội Tin học Việt Nam", 2023, 102));
        repo.add(new Magazine("M002", "Tạp chí Công nghệ Thông tin PTIT", "Học viện BCVT", 2023, 45));
        repo.add(new Magazine("M003", "Tạp chí Khoa học Máy tính", "Viện Hàn lâm KHCN", 2022, 88));
        repo.add(new Magazine("M004", "IEEE Software Magazine", "IEEE Computer Society", 2023, 115));
        repo.add(new Magazine("M005", "Tạp chí An toàn Thông tin", "Ban Cơ yếu Chính phủ", 2023, 67));

       
        repo.add(new Thesis("T001", "Nghiên cứu ứng dụng IoT trong Nông nghiệp", "Hoàng Văn E", 2022, "GS.TS. Hoàng Trung", "PTIT"));
        repo.add(new Thesis("T002", "Phát triển Hệ thống Nhận diện Khuôn mặt", "Vũ Thị F", 2023, "PGS.TS. Nguyễn Nam", "ĐHQG Hà Nội"));
        repo.add(new Thesis("T003", "Tối ưu hóa Thuật toán Phân loại Dữ liệu", "Đỗ Văn G", 2021, "TS. Đặng Thanh", "ĐH Bách Khoa"));
        repo.add(new Thesis("T004", "Ứng dụng Blockchain trong Truy xuất Nguồn gốc", "Ngô Thị H", 2023, "PGS.TS. Trần Đức", "PTIT"));
    }
}