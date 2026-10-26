import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Lop ho tro tao bieu do thong ke (PieChart va BarChart) bang Java Swing + Graphics2D.
 * Phu trách: Sang - Chuc nang Thong ke & Do thi.
 */
public class ChartHelper {

    private static final Color[] PALETTE = {
        new Color(59, 130, 246),   // Blue
        new Color(16, 185, 129),   // Emerald
        new Color(245, 158, 11),   // Amber
        new Color(239, 68, 68),    // Red
        new Color(139, 92, 246),   // Violet
        new Color(236, 72, 153),   // Pink
        new Color(20, 184, 166)    // Teal
    };

    /**
     * Tao JPanel chua PieChart thong ke ty le loai tai lieu.
     *
     * @param title   Tieu de bieu do
     * @param dataMap Map chua (Ten loai tai lieu -> So luong)
     * @return JPanel hien thi PieChart
     */
    public static JPanel createPieChart(String title, Map<String, Integer> dataMap) {
        return new PieChartPanel(title, dataMap);
    }

    /**
     * Tao JPanel chua BarChart thong ke luot muon theo thang.
     *
     * @param title   Tieu de bieu do
     * @param dataMap Map chua (Thang -> So luot muon)
     * @return JPanel hien thi BarChart
     */
    public static JPanel createBarChart(String title, Map<String, Integer> dataMap) {
        return new BarChartPanel(title, dataMap);
    }

    /**
     * Panel ve PieChart (Ty le loai tai lieu).
     */
    private static class PieChartPanel extends JPanel {
        private final String title;
        private final Map<String, Integer> data;

        public PieChartPanel(String title, Map<String, Integer> data) {
            this.title = title;
            this.data = (data != null) ? new LinkedHashMap<>(data) : new LinkedHashMap<>();
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Ve tieu de
            g2.setColor(new Color(30, 41, 59));
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            FontMetrics titleFm = g2.getFontMetrics();
            int titleX = (width - titleFm.stringWidth(title)) / 2;
            g2.drawString(title, Math.max(16, titleX), 30);

            int total = 0;
            for (Integer value : data.values()) {
                if (value != null && value > 0) {
                    total += value;
                }
            }

            if (total == 0 || data.isEmpty()) {
                g2.setColor(new Color(100, 116, 139));
                g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
                String emptyMsg = "Chưa có dữ liệu thống kê";
                int msgX = (width - g2.getFontMetrics().stringWidth(emptyMsg)) / 2;
                g2.drawString(emptyMsg, msgX, height / 2);
                g2.dispose();
                return;
            }

            // Tinh toan kich thuoc hinh tron va chu thich
            int topOffset = 50;
            int availableHeight = height - topOffset - 20;
            int legendWidth = 175;
            int availablePieWidth = width - legendWidth - 40;
            int diameter = Math.max(80, Math.min(availablePieWidth, availableHeight) - 20);

            int pieX = 24 + Math.max(0, (availablePieWidth - diameter) / 2);
            int pieY = topOffset + (availableHeight - diameter) / 2;

            double startAngle = 90.0;
            int colorIndex = 0;

            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                int value = (entry.getValue() != null && entry.getValue() > 0) ? entry.getValue() : 0;
                if (value == 0) continue;

                double angle = (value * 360.0) / total;
                Color sliceColor = PALETTE[colorIndex % PALETTE.length];

                g2.setColor(sliceColor);
                g2.fill(new Arc2D.Double(pieX, pieY, diameter, diameter, startAngle, -angle, Arc2D.PIE));

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                g2.draw(new Arc2D.Double(pieX, pieY, diameter, diameter, startAngle, -angle, Arc2D.PIE));

                // Ve phan tram ben trong lat cat neu goc du lon
                double midAngleRad = Math.toRadians(startAngle - angle / 2.0);
                if (angle >= 18.0) {
                    int labelRadius = (int) (diameter * 0.33);
                    int labelX = pieX + diameter / 2 + (int) (labelRadius * Math.cos(midAngleRad));
                    int labelY = pieY + diameter / 2 - (int) (labelRadius * Math.sin(midAngleRad));

                    String pctText = String.format("%.1f%%", (value * 100.0) / total);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.setColor(Color.WHITE);
                    g2.drawString(pctText, labelX - fm.stringWidth(pctText) / 2, labelY + fm.getAscent() / 3);
                }

                startAngle -= angle;
                colorIndex++;
            }

            // Ve Legend (Chu thich) ben phai
            int legendX = pieX + diameter + 25;
            int legendY = pieY + 15;
            colorIndex = 0;
            g2.setFont(new Font("SansSerif", Font.PLAIN, 13));

            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                int value = (entry.getValue() != null && entry.getValue() > 0) ? entry.getValue() : 0;
                Color sliceColor = PALETTE[colorIndex % PALETTE.length];

                g2.setColor(sliceColor);
                g2.fillRoundRect(legendX, legendY, 14, 14, 4, 4);

                g2.setColor(new Color(51, 65, 85));
                double pct = (value * 100.0) / total;
                String legendLabel = String.format("%s: %d (%.1f%%)", entry.getKey(), value, pct);
                g2.drawString(legendLabel, legendX + 22, legendY + 12);

                legendY += 26;
                colorIndex++;
            }

            g2.dispose();
        }
    }

    /**
     * Panel ve BarChart (Luot muon theo thang).
     */
    private static class BarChartPanel extends JPanel {
        private final String title;
        private final Map<String, Integer> data;

        public BarChartPanel(String title, Map<String, Integer> data) {
            this.title = title;
            this.data = (data != null) ? new LinkedHashMap<>(data) : new LinkedHashMap<>();
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Ve tieu de
            g2.setColor(new Color(30, 41, 59));
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            FontMetrics titleFm = g2.getFontMetrics();
            int titleX = (width - titleFm.stringWidth(title)) / 2;
            g2.drawString(title, Math.max(16, titleX), 30);

            if (data.isEmpty()) {
                g2.setColor(new Color(100, 116, 139));
                g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
                String emptyMsg = "Chưa có dữ liệu lượt mượn";
                int msgX = (width - g2.getFontMetrics().stringWidth(emptyMsg)) / 2;
                g2.drawString(emptyMsg, msgX, height / 2);
                g2.dispose();
                return;
            }

            int maxVal = 0;
            for (Integer val : data.values()) {
                if (val != null && val > maxVal) {
                    maxVal = val;
                }
            }
            int yAxisMax = Math.max(5, (int) Math.ceil(maxVal * 1.15));

            int leftPad = 52;
            int rightPad = 24;
            int topPad = 56;
            int bottomPad = 48;

            int chartWidth = Math.max(100, width - leftPad - rightPad);
            int chartHeight = Math.max(100, height - topPad - bottomPad);

            // Ve luoi ngang va nhan truc tung (Y-axis)
            int gridSteps = 5;
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            for (int i = 0; i <= gridSteps; i++) {
                int y = topPad + chartHeight - (i * chartHeight / gridSteps);
                int labelVal = (int) Math.round((yAxisMax * i) / (double) gridSteps);

                g2.setColor(new Color(226, 232, 240));
                g2.drawLine(leftPad, y, leftPad + chartWidth, y);

                g2.setColor(new Color(100, 116, 139));
                String yLabel = String.valueOf(labelVal);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(yLabel, leftPad - fm.stringWidth(yLabel) - 8, y + 4);
            }

            // Ve truc X va Y
            g2.setColor(new Color(148, 163, 184));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(leftPad, topPad, leftPad, topPad + chartHeight);
            g2.drawLine(leftPad, topPad + chartHeight, leftPad + chartWidth, topPad + chartHeight);

            // Ve cac cot (Bars)
            int count = data.size();
            double slotWidth = (double) chartWidth / count;
            int barWidth = Math.max(12, Math.min(48, (int) (slotWidth * 0.6)));

            int index = 0;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                int val = (entry.getValue() != null && entry.getValue() > 0) ? entry.getValue() : 0;
                int barHeight = (int) Math.round((val / (double) yAxisMax) * chartHeight);

                int barX = leftPad + (int) (index * slotWidth + (slotWidth - barWidth) / 2);
                int barY = topPad + chartHeight - barHeight;

                // Mau cot
                g2.setColor(new Color(59, 130, 246));
                if (barHeight > 0) {
                    g2.fill(new RoundRectangle2D.Double(barX, barY, barWidth, barHeight, 6, 6));
                }

                // Gia tri tren dau cot
                g2.setColor(new Color(30, 41, 59));
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
                String valStr = String.valueOf(val);
                FontMetrics valFm = g2.getFontMetrics();
                g2.drawString(valStr, barX + (barWidth - valFm.stringWidth(valStr)) / 2, barY - 6);

                // Nhan thang duoi truc X
                g2.setColor(new Color(71, 85, 105));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                String monthLabel = entry.getKey();
                FontMetrics monthFm = g2.getFontMetrics();
                g2.drawString(
                    monthLabel,
                    barX + (barWidth - monthFm.stringWidth(monthLabel)) / 2,
                    topPad + chartHeight + 20
                );

                index++;
            }

            g2.dispose();
        }
    }
}