package vn.edu.ptit.oop.nhom09.ui.dialogs;

import vn.edu.ptit.oop.nhom09.service.FineCalculator;
import vn.edu.ptit.oop.nhom09.service.BookFine;
import vn.edu.ptit.oop.nhom09.service.MagazineFine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;
import java.text.DecimalFormat;

public class LoanDialog extends JDialog {

    // Các thành phần giao diện
    private JTextField txtBorrower;
    private JTextField txtPhone;
    private JTextField txtDocument;
    private JTextField txtDueDate;

    private JComboBox<String> cboDocumentType;

    private JLabel lblStatus;
    private JLabel lblFine;

    private JButton btnBorrow;
    private JButton btnReturn;
    private JButton btnClose;

    // Trạng thái mượn tài liệu
    private boolean borrowed = false;

    private LocalDate borrowDate;
    private LocalDate dueDate;

    private final DecimalFormat moneyFormat =
            new DecimalFormat("#,###");

    // Constructor
    public LoanDialog(Frame owner) {

        super(owner, "Quản lý mượn / trả tài liệu", true);

        initComponents();

        setSize(550, 450);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Khởi tạo giao diện
    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));

        mainPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        // Tiêu đề
        JLabel lblTitle = new JLabel(
                "QUẢN LÝ MƯỢN / TRẢ TÀI LIỆU",
                SwingConstants.CENTER
        );

        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        lblTitle.setForeground(new Color(30, 90, 160));

        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Form nhập thông tin
        JPanel formPanel = new JPanel(
                new GridLayout(6, 2, 10, 15)
        );

        txtBorrower = new JTextField();
        txtPhone = new JTextField();
        txtDocument = new JTextField();

        cboDocumentType = new JComboBox<>(
                new String[]{"Sách", "Tạp chí"}
        );

        txtDueDate = new JTextField(
                LocalDate.now().plusDays(14).toString()
        );

        lblStatus = new JLabel("Chưa mượn");

        lblStatus.setForeground(Color.GRAY);

        formPanel.add(new JLabel("Tên người mượn:"));
        formPanel.add(txtBorrower);

        formPanel.add(new JLabel("Số điện thoại:"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Tên tài liệu:"));
        formPanel.add(txtDocument);

        formPanel.add(new JLabel("Loại tài liệu:"));
        formPanel.add(cboDocumentType);

        formPanel.add(new JLabel("Ngày phải trả:"));
        formPanel.add(txtDueDate);

        formPanel.add(new JLabel("Trạng thái:"));
        formPanel.add(lblStatus);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Khu vực phía dưới
        JPanel bottomPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        lblFine = new JLabel(
                "Tiền phạt: 0 VNĐ",
                SwingConstants.CENTER
        );

        lblFine.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        bottomPanel.add(lblFine, BorderLayout.NORTH);

        // Các nút chức năng
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        btnBorrow = new JButton("Mượn tài liệu");
        btnReturn = new JButton("Trả tài liệu");
        btnClose = new JButton("Đóng");

        btnReturn.setEnabled(false);

        buttonPanel.add(btnBorrow);
        buttonPanel.add(btnReturn);
        buttonPanel.add(btnClose);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Xử lý sự kiện
        btnBorrow.addActionListener(e -> borrowDocument());

        btnReturn.addActionListener(e -> returnDocument());

        btnClose.addActionListener(e -> dispose());
    }

    // Chức năng mượn tài liệu
    private void borrowDocument() {

        String borrower = txtBorrower.getText().trim();
        String phone = txtPhone.getText().trim();
        String document = txtDocument.getText().trim();

        // Kiểm tra dữ liệu đầu vào
        if (borrower.isEmpty()
                || phone.isEmpty()
                || document.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Kiểm tra số điện thoại
        if (!phone.matches("\\d{10,11}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Số điện thoại phải gồm 10 hoặc 11 chữ số!",
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            dueDate = LocalDate.parse(
                    txtDueDate.getText().trim()
            );

            borrowDate = LocalDate.now();

            // Ngày trả phải sau ngày mượn
            if (!dueDate.isAfter(borrowDate)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ngày trả phải sau ngày mượn!",
                        "Lỗi ngày tháng",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            borrowed = true;

            lblStatus.setText("Đang mượn");
            lblStatus.setForeground(Color.BLUE);

            btnBorrow.setEnabled(false);
            btnReturn.setEnabled(true);

            // Khóa thông tin sau khi mượn
            txtBorrower.setEditable(false);
            txtPhone.setEditable(false);
            txtDocument.setEditable(false);
            txtDueDate.setEditable(false);

            cboDocumentType.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "MƯỢN TÀI LIỆU THÀNH CÔNG!"
                    + "\nNgười mượn: " + borrower
                    + "\nSố điện thoại: " + phone
                    + "\nTài liệu: " + document
                    + "\nLoại tài liệu: "
                    + cboDocumentType.getSelectedItem()
                    + "\nNgày mượn: " + borrowDate
                    + "\nNgày phải trả: " + dueDate,
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ngày không hợp lệ!\n"
                    + "Vui lòng nhập theo dạng yyyy-MM-dd.",
                    "Lỗi ngày tháng",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Chức năng trả tài liệu
    private void returnDocument() {

        if (!borrowed) {

            JOptionPane.showMessageDialog(
                    this,
                    "Không có tài liệu đang mượn!"
            );

            return;
        }

        LocalDate returnDate = LocalDate.now();

        // Tính số ngày trễ hạn
        long overdueDays = ChronoUnit.DAYS.between(
                dueDate,
                returnDate
        );

        // Sử dụng interface để áp dụng đa hình
        FineCalculator calculator;

        String documentType =
                (String) cboDocumentType.getSelectedItem();

        if ("Sách".equals(documentType)) {

            calculator = new BookFine();

        } else {

            calculator = new MagazineFine();
        }

        // Gọi phương thức tính phạt
        double fine = calculator.calculateFine(overdueDays);

        // Hiển thị tiền phạt
        lblFine.setText(
                "Tiền phạt: "
                + moneyFormat.format(fine)
                + " VNĐ"
        );

        lblStatus.setText("Đã trả");
        lblStatus.setForeground(new Color(0, 150, 0));

        borrowed = false;

        btnReturn.setEnabled(false);

        JOptionPane.showMessageDialog(
                this,
                "TRẢ TÀI LIỆU THÀNH CÔNG!"
                + "\nNgày mượn: " + borrowDate
                + "\nNgày phải trả: " + dueDate
                + "\nNgày trả thực tế: " + returnDate
                + "\nSố ngày trễ: "
                + Math.max(0, overdueDays)
                + "\nTiền phạt: "
                + moneyFormat.format(fine) + " VNĐ",
                "Kết quả trả tài liệu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

