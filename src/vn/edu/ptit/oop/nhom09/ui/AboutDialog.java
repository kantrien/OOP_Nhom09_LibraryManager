package vn.edu.ptit.oop.nhom09.ui;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parent) {
        super(parent, "Thông tin Nhóm Phát Triển - Đồ án OOP", true);
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("DỰ ÁN QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, BorderLayout.NORTH);

        // Danh sách 10 thành viên trong nhóm
        String[] members = {
            "1. Tâm - Mô hình hóa dữ liệu (LibraryItem, Book, Magazine, Thesis)",
            "2. Minh - Quản lý kho dữ liệu (LibraryRepository, MockDataGenerator)",
            "3. Hiếu - Lưu trữ & Tiện ích (FileService, ValidationUtil)",
            "4. Sơn - Xử lý JTable (ItemTableModel, LoanTableCellRenderer)",
            "5. Trung Nguyên - Giao diện Thêm/Sửa (ItemFormDialog, FormUIHelper)",
            "6. Vương - Nghiệp vụ Mượn/Trả (LoanRecord, LoanService)",
            "7. Lâm - Tính tiền phạt & Đa hình (LoanDialog, FineCalculator)",
            "8. Sang - Thống kê & Đồ thị (ChartWindow, ChartHelper)",
            "9. Hải (N24DCAT023) - Xử lý lỗi & Kiểm thử (Exceptions, AboutDialog, TestCases)",
            "10. Kiên - Hỗ trợ tích hợp hệ thống & Kiểm thử giao diện"
        };

        JList<String> listMembers = new JList<>(members);
        listMembers.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(listMembers);
        add(scrollPane, BorderLayout.CENTER);

        // Nút Đóng
        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Arial", Font.BOLD, 12));
        btnClose.addActionListener(e -> dispose());
        
        JPanel panelBottom = new JPanel();
        panelBottom.add(btnClose);
        add(panelBottom, BorderLayout.SOUTH);
    }
}
