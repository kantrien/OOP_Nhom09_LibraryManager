package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Quản lý CardLayout: mỗi loại tài liệu là một card (một TypeFieldPanel).
 * Thêm loại mới = viết thêm 1 TypeFieldPanel và đưa vào danh sách, không sửa lớp này (Open-Closed).
 */
public final class FormUIHelper {

    private final CardLayout cards = new CardLayout();
    private final JPanel container = new JPanel(cards);
    private final Map<String, TypeFieldPanel> panels = new LinkedHashMap<>();
    private String currentType;

    public FormUIHelper(List<TypeFieldPanel> typePanels) {
        // TODO: với mỗi panel → panels.put(p.typeName(), p) và container.add(p, p.typeName())
        // TODO: currentType = loại đầu tiên
        // Câu hỏi: nếu 2 panel trùng typeName() thì sao? Nên xử lý thế nào?
    }

    /** Tạo ComboBox chứa tên các loại; đổi lựa chọn → đổi card. */
    public JComboBox<String> createTypeCombo() {
        // TODO: new JComboBox<>(panels.keySet().toArray(new String[0]))
        // TODO: addActionListener → show((String) combo.getSelectedItem())
        throw new UnsupportedOperationException("TODO");
    }

    /** Hiển thị card ứng với loại tài liệu. */
    public void show(String typeName) {
        // TODO: kiểm tra typeName có trong map không (fail-secure), rồi cards.show(...)
    }

    /** Panel của loại đang chọn. */
    public TypeFieldPanel current() {
        return panels.get(currentType);
    }

    public JPanel getContainer() {
        return container;
    }
}
