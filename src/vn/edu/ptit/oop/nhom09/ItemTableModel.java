package vn.edu.ptit.oop.nhom09;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;

public class ItemTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Mã", "Loại", "Tên tài liệu", "Tác giả", "Năm xuất bản"
    };

    private List<LibraryItem> items;

    public ItemTableModel() {
        this.items = new ArrayList<>();
    }

    public ItemTableModel(List<LibraryItem> items) {
        this.items = copyOf(items);
    }

    public void setItems(List<LibraryItem> items) {
        this.items = copyOf(items);
        fireTableDataChanged();
    }

    public LibraryItem getItemAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= items.size()) {
            return null;
        }
        return items.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LibraryItem item = items.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getId();
            case 1:
                return item.getTypeName();
            case 2:
                return item.getTitle();
            case 3:
                return item.getAuthor();
            case 4:
                return String.valueOf(item.getYear());
            default:
                return "";
        }
    }

    private static List<LibraryItem> copyOf(List<LibraryItem> source) {
        if (source == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(source);
    }
}