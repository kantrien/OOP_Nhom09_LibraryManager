package vn.edu.ptit.oop.nhom09;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.service.LoanService;

public class LoanTableCellRenderer extends DefaultTableCellRenderer {

    private static final Color OVERDUE_BACKGROUND = new Color(255, 205, 210);
    private static final Color OVERDUE_FOREGROUND = new Color(183, 28, 28);
    private static final Color OVERDUE_SELECTED_BACKGROUND = new Color(239, 154, 154);
    private static final Color OVERDUE_SELECTED_FOREGROUND = new Color(100, 0, 0);

    private final LoanService loanService;

    public LoanTableCellRenderer(LoanService loanService) {
        this.loanService = Objects.requireNonNull(loanService, "LoanService không được để trống");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setBackground(null);
        setForeground(null);
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        TableModel model = table.getModel();
        if (model instanceof ItemTableModel) {
            int modelRow = table.convertRowIndexToModel(row);
            LibraryItem item = ((ItemTableModel) model).getItemAt(modelRow);
            if (item != null && loanService.isOverdue(item.getId(), LocalDate.now())) {
                if (isSelected) {
                    component.setBackground(OVERDUE_SELECTED_BACKGROUND);
                    component.setForeground(OVERDUE_SELECTED_FOREGROUND);
                } else {
                    component.setBackground(OVERDUE_BACKGROUND);
                    component.setForeground(OVERDUE_FOREGROUND);
                }
            }
        }
        return component;
    }
}