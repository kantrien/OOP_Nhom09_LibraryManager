package vn.edu.ptit.oop.nhom09.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import vn.edu.ptit.oop.nhom09.common.BaseWindow;

public class MainWindow extends BaseWindow {

    private JTable table;

    public MainWindow() {
        super("Nhóm 09 - Quản lý Thư viện", 1100, 700);
    }

    @Override
    protected void initUI() {
        // Nút thêm tài liệu (tạm thời để trống hành động cho đến khi có ItemFormDialog)
        addToolButton("/resources/icons/add.png", "Thêm tài liệu", e -> openAddDialog());

        // Tạm thời tạo giao diện chờ cho đến khi ghép Repository và ItemTableModel
        JLabel placeholderLabel = new JLabel("Danh sách tài liệu thư viện (Đang cập nhật...)", SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);

        setStatus("Đã nạp hệ thống thành công");
    }

    private void openAddDialog() {
        // Phương thức mở Dialog thêm tài liệu (các bạn làm UI Dialog sẽ hoàn thiện sau)
        System.out.println("Mở dialog thêm tài liệu");
    }

    @Override
    protected void showAbout() {
        super.showAbout();
    }
}
