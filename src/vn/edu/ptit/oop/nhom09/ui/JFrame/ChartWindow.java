import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Cua so hien thi Thong ke & Do thi (Ke thua JFrame, su dung DISPOSE_ON_CLOSE).
 * Phu trach: Sang - Chuc nang Thong ke & Do thi.
 */
public class ChartWindow extends JFrame {

    private Map<String, Integer> itemTypeData;
    private Map<String, Integer> monthlyBorrowData;

    /**
     * Constructor mac dinh voi du lieu mau de kiem thu giao dien.
     */
    public ChartWindow() {
        this(createSampleItemTypeData(), createSampleMonthlyBorrowData());
    }

    /**
     * Constructor nhan du lieu thong ke thuc te tu he thong quan ly thu vien.
     *
     * @param itemTypeData      Map chua ty le loai tai lieu (Loai tai lieu -> So luong)
     * @param monthlyBorrowData Map chua luot muon theo thang (Thang -> So luot muon)
     */
    public ChartWindow(Map<String, Integer> itemTypeData, Map<String, Integer> monthlyBorrowData) {
        this.itemTypeData = itemTypeData;
        this.monthlyBorrowData = monthlyBorrowData;
        initUI();
    }

    private void initUI() {
        setTitle("Thống kê & Đồ thị - Quản lý Thư viện");
        setSize(980, 560);
        setLocationRelativeTo(null);
        // Dung DISPOSE_ON_CLOSE de khi dong cua so thong ke khong lam tat chuong trinh chinh
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(16, 16));
        mainPanel.setBackground(new Color(248, 250, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Header
        JLabel headerLabel = new JLabel("BÁO CÁO THỐNG KÊ & ĐỒ THỊ THƯ VIỆN", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setForeground(new Color(15, 23, 42));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 8, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Panel chua 2 bieu do: PieChart (trai) va BarChart (phai)
        JPanel chartsGrid = new JPanel(new GridLayout(1, 2, 16, 16));
        chartsGrid.setOpaque(false);

        JPanel pieChartPanel = ChartHelper.createPieChart("Tỷ lệ loại tài liệu", itemTypeData);
        JPanel barChartPanel = ChartHelper.createBarChart("Lượt mượn theo tháng", monthlyBorrowData);

        chartsGrid.add(pieChartPanel);
        chartsGrid.add(barChartPanel);
        mainPanel.add(chartsGrid, BorderLayout.CENTER);

        // Footer chua nut Dong
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        JButton closeButton = new JButton("Đóng cửa sổ");
        closeButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        footerPanel.add(closeButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private static Map<String, Integer> createSampleItemTypeData() {
        Map<String, Integer> sample = new LinkedHashMap<>();
        sample.put("Sách (Book)", 120);
        sample.put("Tạp chí (Magazine)", 45);
        sample.put("Luận văn (Thesis)", 30);
        sample.put("Tài liệu số (Digital)", 25);
        return sample;
    }

    private static Map<String, Integer> createSampleMonthlyBorrowData() {
        Map<String, Integer> sample = new LinkedHashMap<>();
        sample.put("T1", 32);
        sample.put("T2", 28);
        sample.put("T3", 45);
        sample.put("T4", 50);
        sample.put("T5", 62);
        sample.put("T6", 38);
        sample.put("T7", 25);
        sample.put("T8", 30);
        sample.put("T9", 55);
        sample.put("T10", 68);
        sample.put("T11", 49);
        sample.put("T12", 40);
        return sample;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChartWindow window = new ChartWindow();
            window.setVisible(true);
        });
    }
}