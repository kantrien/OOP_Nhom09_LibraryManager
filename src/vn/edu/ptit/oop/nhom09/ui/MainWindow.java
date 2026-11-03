package vn.edu.ptit.oop.nhom09.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableRowSorter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import vn.edu.ptit.oop.nhom09.ItemTableModel;
import vn.edu.ptit.oop.nhom09.LoanTableCellRenderer;
import vn.edu.ptit.oop.nhom09.common.BaseWindow;
import vn.edu.ptit.oop.nhom09.exception.ItemNotFoundException;
import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.repository.InMemoryLibraryRepository;
import vn.edu.ptit.oop.nhom09.repository.LibraryRepository;
import vn.edu.ptit.oop.nhom09.util.FileService;
import vn.edu.ptit.oop.nhom09.service.LoanService;
import vn.edu.ptit.oop.nhom09.ui.dialogs.ItemFormDialog;
import vn.edu.ptit.oop.nhom09.ui.dialogs.LoanDialog;
import vn.edu.ptit.oop.nhom09.repository.MockDataGenerator;


public class MainWindow extends BaseWindow {
    private JLabel lblId, lblType, lblTitle, lblAuthor, lblYear, lblStatus;
    private JTable table;
    private ItemTableModel tableModel;
    
    private LibraryRepository repository;
    private LoanService loanService;

    public MainWindow() {
        super("Nhóm 09 - Quản lý Thư viện", 1100, 700);
    }

   @Override
protected void initUI() {

        JButton btnAbout = new JButton("Thông tin Nhóm");
    btnAbout.addActionListener(e -> {
        vn.edu.ptit.oop.nhom09.ui.AboutDialog aboutDialog = new vn.edu.ptit.oop.nhom09.ui.AboutDialog(this);
        aboutDialog.setVisible(true);
    });
toolBar.add(btnAbout);
    addToolButton("/resources/icons/add.png", "Thêm tài liệu", e -> openAddDialog());
    addToolButton("/resources/icons/loan.png", "Mượn/Trả", e -> openLoanDialog());
    addToolButton("/resources/icons/delete.png", "Xóa tài liệu", e -> handleDeleteItem());
    addToolButton("/resources/icons/chart.png", "Thống kê", e -> openChartWindow());
    addToolButton("/resources/icons/edit.png", "Sửa tài liệu", e -> handleEditItem());
    // Nút lưu file
    addToolButton("/resources/icons/save.png", "Lưu dữ liệu ra file", e -> handleSaveFile());

    // Nút Refresh (
    addToolButton("/resources/icons/refresh.png", "Làm mới bảng", e -> refreshTable());

// 2. Tạo khu vực bên trái (Thanh tìm kiếm + Bảng dữ liệu)
    JPanel leftPanel = new JPanel(new BorderLayout());
    
    // --- Thanh tìm kiếm ---
    JPanel searchPanel = new JPanel(new BorderLayout());
    searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    searchPanel.add(new JLabel("Tìm kiếm (Tên/Tác giả): "), BorderLayout.WEST);
    JTextField txtSearch = new JTextField();
    searchPanel.add(txtSearch, BorderLayout.CENTER);
    leftPanel.add(searchPanel, BorderLayout.NORTH);
    
    try {
    // 1. Khởi tạo các biến toàn cục 
    repository = new InMemoryLibraryRepository();
    loanService = new LoanService(); 

    // 2. Sinh dữ liệu mẫu
    MockDataGenerator.generateSampleData(repository); 
    List<LibraryItem> initialData = repository.findAll();

    // 3. Đưa dữ liệu vào TableModel
    tableModel = new ItemTableModel();
    tableModel.setItems(initialData); 

    // 4. Khởi tạo JTable
    table = new JTable(tableModel);
    
    // 5. Cài đặt Renderer 
    table.setDefaultRenderer(Object.class, new LoanTableCellRenderer(this.loanService));
    
} catch (Exception e) {
    showError("Lỗi khởi tạo dữ liệu mẫu: " + e.getMessage());
    tableModel = new ItemTableModel(); 
    table = new JTable(tableModel); 
}

    // --- Bảng dữ liệu (JTable) ---
    
    TableRowSorter<ItemTableModel> rowSorter = new TableRowSorter<>(tableModel);
    table.setRowSorter(rowSorter);
    JScrollPane scrollPane = new JScrollPane(table);
    leftPanel.add(scrollPane, BorderLayout.CENTER);


    // 2. Bắt sự kiện gõ phím trên ô txtSearch
txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
    }

    // Hàm xử lý logic lọc dữ liệu
    private void filterTable() {
        String text = txtSearch.getText();
        
        if (text.trim().length() == 0) {
            // Nếu ô tìm kiếm trống, xóa bộ lọc để hiển thị toàn bộ bảng
            rowSorter.setRowFilter(null);
        } else {
            // Lọc dữ liệu dựa trên cột Tên (index 2) và Tác giả (index 3)
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2, 3));
        }
    }
});
    int viewRow = table.getSelectedRow();
    if (viewRow >= 0) {
        // Chuyển đổi chỉ số từ giao diện (view) sang dữ liệu gốc (model)
        int modelRow = table.convertRowIndexToModel(viewRow); 
        
        // Dùng modelRow để lấy dữ liệu thay vì viewRow
        String itemId = (String) table.getModel().getValueAt(modelRow, 0); 
    }

    // 3. Tạo khu vực bên phải (Panel hiển thị chi tiết tài liệu)
    // 3. Tạo khu vực bên phải (Panel hiển thị chi tiết tài liệu)
    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
    rightPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết tài liệu"));
    rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Chi tiết tài liệu"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Thêm padding cho đẹp

    // Khởi tạo các thành phần hiển thị
    lblId = new JLabel("Mã: ");
    lblType = new JLabel("Loại: ");
    lblTitle = new JLabel("Tên tài liệu: ");
    lblAuthor = new JLabel("Tác giả: ");
    lblYear = new JLabel("Năm xuất bản: ");
    lblStatus = new JLabel("Trạng thái: ");

    // Thêm vào panel
    rightPanel.add(lblId);
    rightPanel.add(Box.createVerticalStrut(10)); // Tạo khoảng trống giữa các dòng
    rightPanel.add(lblType);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(lblTitle);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(lblAuthor);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(lblYear);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(lblStatus);

    // Gắn sự kiện lắng nghe khi người dùng click vào dòng trên JTable
    table.getSelectionModel().addListSelectionListener(e -> {
        // Kiểm tra xem người dùng đã nhả chuột ra chưa (tránh sự kiện kích hoạt 2 lần)
        if (!e.getValueIsAdjusting()) {
            int selectedViewRow = table.getSelectedRow();
            if (selectedViewRow >= 0) {
                // Chuyển đổi dòng từ giao diện sang dòng dữ liệu thực tế
                int modelRow = table.convertRowIndexToModel(selectedViewRow);
                
                // Lấy đối tượng LibraryItem từ ItemTableModel
                LibraryItem item = tableModel.getItemAt(modelRow);
                
                if (item != null) {
                    // Cập nhật thông tin lên Panel bên phải
                    lblId.setText("Mã: " + item.getId());
                    lblType.setText("Loại: " + item.getTypeName());
                    lblTitle.setText("Tên tài liệu: " + item.getTitle());
                    lblAuthor.setText("Tác giả: " + item.getAuthor());
                    lblYear.setText("Năm xuất bản: " + item.getYear());
                    
                    // Giả sử LibraryItem có hàm isAvailable()
                    String status = item.isAvailable() ? "Còn trong thư viện" : "Đang được mượn";
                    lblStatus.setText("Trạng thái: " + status);
                }
            }
        }
    });

    // Gắn sự kiện khi nhấn phím Enter (trên bàn phím) tại ô tìm kiếm
txtSearch.addActionListener(e -> {
    String keyword = txtSearch.getText().trim().toLowerCase();
    
    if (keyword.isEmpty()) {
       
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hoặc tác giả cần tìm!");
        return;
    }

    try {
        // Tạo một danh sách rỗng để chứa kết quả tìm được
        java.util.List<vn.edu.ptit.oop.nhom09.model.LibraryItem> results = new java.util.ArrayList<>();
        
        // Quét toàn bộ kho để tìm sách có TÊN hoặc TÁC GIẢ chứa từ khóa
        for (vn.edu.ptit.oop.nhom09.model.LibraryItem item : repository.findAll()) {
            if (item.getTitle().toLowerCase().contains(keyword) || 
                item.getAuthor().toLowerCase().contains(keyword)) {
                results.add(item);
            }
        }
        if (results.isEmpty()) {
            throw new vn.edu.ptit.oop.nhom09.exception.ItemNotFoundException(
                "Không tìm thấy tài liệu nào có tên hoặc tác giả chứa: " + keyword
            );
        }
        tableModel.setItems(results); 
        
    } catch (vn.edu.ptit.oop.nhom09.exception.ItemNotFoundException ex) {
        // Bắt lỗi và hiện hộp thoại thông báo
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
    }
});

    // 4. Ghép 2 bên vào JSplitPane
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    splitPane.setDividerLocation(700); // Đặt vị trí thanh chia đôi
    
    // Thêm splitPane vào trung tâm cửa sổ
    add(splitPane, BorderLayout.CENTER);

    setStatus("Đã nạp hệ thống thành công");
   
}

    // --- CÁC HÀM XỬ LÝ SỰ KIỆN ---
    
    private void openAddDialog() {
    ItemFormDialog dialog = new ItemFormDialog(this, null);
    dialog.setVisible(true); // Chương trình sẽ tạm dừng ở đây chờ người dùng nhập và đóng form


    LibraryItem newItem = dialog.getResult(); 
    
    if (newItem != null) {
        try {
            repository.add(newItem); 
            refreshTable();          // Cập nhật lại giao diện
            showInfo("Thêm tài liệu thành công!");
        } catch (Exception ex) {
            showError("Lỗi khi thêm: " + ex.getMessage());
        }
    }
}

private void openLoanDialog() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showError("Vui lòng chọn một tài liệu trong bảng để mượn/trả!");
        return;
    }

    // Lấy tài liệu đang được chọn
    int modelRow = table.convertRowIndexToModel(selectedRow);
    LibraryItem selectedItem = tableModel.getItemAt(modelRow);

    // Truyền this, loanService và tài liệu được chọn vào Dialog
    LoanDialog dialog = new LoanDialog(this, loanService, selectedItem);
    dialog.setVisible(true);
    
    // Refresh lại bảng sau khi mượn/trả xong
    refreshTable();
}
private void handleEditItem() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showError("Vui lòng chọn 1 tài liệu trong bảng để sửa!");
        return;
    }

    int modelRow = table.convertRowIndexToModel(selectedRow);
    LibraryItem selectedItem = tableModel.getItemAt(modelRow);

    // Truyền cuốn sách đang chọn vào để form hiện sẵn dữ liệu cũ
    ItemFormDialog dialog = new ItemFormDialog(this, selectedItem);
    dialog.setVisible(true); 

    // Form đã đóng. Lấy dữ liệu sách sau khi sửa
    LibraryItem editedItem = dialog.getResult(); 
    
    if (editedItem != null) {
        try {
            repository.remove(selectedItem.getId()); 
            repository.add(editedItem);              
            refreshTable();
            showInfo("Cập nhật tài liệu thành công!");
        } 
        catch (Exception ex) {
            showError("Lỗi khi cập nhật: " + ex.getMessage());
        }
}
}

    private void handleDeleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Vui lòng chọn 1 tài liệu trong bảng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa tài liệu này?", "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int modelRow = table.convertRowIndexToModel(selectedRow);
                String itemId = tableModel.getValueAt(modelRow, 0).toString(); 
                
                repository.remove(itemId);
                refreshTable();
                setStatus("Đã xóa thành công mã: " + itemId);
            } catch (Exception ex) {
                showError("Lỗi xóa tài liệu: " + ex.getMessage());
            }
        }
    }

    private void openChartWindow() {

    Map<String, Integer> itemTypeData = new LinkedHashMap<>();
    for (vn.edu.ptit.oop.nhom09.model.LibraryItem item : repository.findAll()) {
        String type = item.getTypeName(); 
        itemTypeData.put(type, itemTypeData.getOrDefault(type, 0) + 1);
    }

    // 2. TÍNH TOÁN LƯỢT MƯỢN THEO THÁNG (TỪ LOAN SERVICE)
    Map<String, Integer> monthlyData = new LinkedHashMap<>();
    
    for (int i = 1; i <= 12; i++) {
        monthlyData.put("T" + i, 0);
    }

    if (loanService != null && loanService.getAllLoans() != null) {
        for (vn.edu.ptit.oop.nhom09.model.LoanRecord record : loanService.getAllLoans()) {
            if (record.getLoanDate() != null) {
  
                int month = record.getLoanDate().getMonthValue();
                String monthKey = "T" + month;
                
                monthlyData.put(monthKey, monthlyData.get(monthKey) + 1);
            }
        }
    }

    vn.edu.ptit.oop.nhom09.ui.ChartWindow chartWindow = new vn.edu.ptit.oop.nhom09.ui.ChartWindow(itemTypeData, monthlyData);
    chartWindow.setVisible(true);
}

    private void handleSaveFile() {
   
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try {
                List<String[]> csvData = new ArrayList<>();
                int rowCount = tableModel.getRowCount();
                int colCount = tableModel.getColumnCount();

                // Lấy Header
                String[] header = new String[colCount];
                for (int j = 0; j < colCount; j++) {
                    header[j] = tableModel.getColumnName(j);
                }
                csvData.add(header);

                // Lấy Data từng dòng
                for (int i = 0; i < rowCount; i++) {
                    String[] rowData = new String[colCount];
                    for (int j = 0; j < colCount; j++) {
                        Object cellValue = tableModel.getValueAt(i, j);
                        rowData[j] = (cellValue == null) ? "" : cellValue.toString();
                    }
                    csvData.add(rowData);
                }

                // Gọi FileService để ghi
                FileService.writeCSV(filePath, csvData);
                showInfo("Lưu file CSV thành công vào:\n" + filePath);
                
            } catch (Exception ex) {
                showError("Lỗi lưu file: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    public void refreshTable() {
    if (repository != null && tableModel != null) {
        List<LibraryItem> latestData = repository.findAll();
        tableModel.setItems(latestData);
    }
    
}

@Override
protected void showAbout() {
    // Khởi tạo và hiển thị form About của nhóm
    AboutDialog dialog = new AboutDialog(this);
    dialog.setVisible(true);
    

}
}