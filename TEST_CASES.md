# BỘ TEST CASES - DỰ ÁN QUẢN LÝ THƯ VIỆN (OOP)

Phụ trách: Phan Ngoc Hai - Xử lý lỗi & Kiểm thử

| STT | Tên Test Case | Mục tiêu kiểm thử | Dữ liệu đầu vào (Input) | Kết quả kỳ vọng (Expected Output) | Trạng thái |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **TC01** | Thêm sách hợp lệ | Kiểm tra tính năng thêm Book mới vào hệ thống | Tên: "Java OOP", ISBN: "978-0134685991", Năm: 2023 | Thêm thành công, hiển thị trên `JTable` | **Pass** |
| **TC02** | Thêm tài liệu lỗi ISBN | Kiểm tra validate ISBN bằng `ValidationUtil` | ISBN định dạng sai: "12345" | Ném ngoại lệ `InvalidDataException`, báo lỗi nhập sai ISBN | **Pass** |
| **TC03** | Tìm kiếm tài liệu tồn tại | Kiểm tra chức năng tìm kiếm theo ID/Tên | Nhập từ khóa: "Java OOP" | Trả về thông tin chính xác của cuốn sách | **Pass** |
| **TC04** | Tìm kiếm tài liệu không tồn tại | Kiểm tra ngoại lệ khi không thấy dữ liệu | Nhập ID không có thật: "B999" | Ném ngoại lệ `ItemNotFoundException` | **Pass** |
| **TC05** | Đọc/Ghi file CSV | Kiểm tra module `FileService` lưu dữ liệu | Danh sách hiện tại gồm 15 tài liệu mẫu | Lưu và đọc lại dữ liệu từ file `.csv` chính xác 100% | **Pass** |
| **TC06** | Tạo phiếu mượn hợp lệ | Kiểm tra nghiệp vụ Mượn (`LoanService`) | User: "Phan Ngoc Hai", Mã tài liệu: "B01" | Tạo phiếu mượn thành công, cập nhật trạng thái đã mượn | **Pass** |
| **TC07** | Mượn trùng tài liệu | Kiểm tra ràng buộc tài liệu đã có người mượn | Mượn lại tài liệu "B01" đang được mượn | Cảnh báo hoặc ném lỗi tài liệu không khả dụng | **Pass** |
| **TC08** | Tính tiền phạt quá hạn (Book) | Kiểm tra tính đa hình `FineCalculator` / `BookFine` | Sách trả trễ 3 hạn | Tính đúng số tiền phạt theo công thức | **Pass** |
| **TC09** | Hiển thị quá hạn trên JTable | Kiểm tra `LoanTableCellRenderer` đổi màu dòng | Bản ghi có hạn trả < Ngày hiện tại | Dòng tương ứng trên `JTable` chuyển sang màu đỏ | **Pass** |
| **TC10** | Hiển thị hộp thoại About | Kiểm tra giao diện `AboutDialog` | Click menu "About" | Hiển thị đúng bảng thông tin 10 thành viên nhóm | **Pass** |