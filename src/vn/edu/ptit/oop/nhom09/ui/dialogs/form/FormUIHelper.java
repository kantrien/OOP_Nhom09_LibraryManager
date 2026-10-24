package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public final class FormUIHelper {

    private final CardLayout cards = new CardLayout();
    private final JPanel container = new JPanel(cards);
    private final Map<String, TypeFieldPanel> panels = new LinkedHashMap<>();
    private String currentType;

    public FormUIHelper(List<TypeFieldPanel> typePanels) {
        if (typePanels == null || typePanels.isEmpty()) {
            throw new IllegalArgumentException("Cần ít nhất một loại tài liệu");
        }
        for (TypeFieldPanel p : typePanels) {
            String key = p.typeName();
            if (panels.containsKey(key)) {
                throw new IllegalArgumentException("Trùng loại tài liệu: " + key);
            }
            panels.put(key, p);
            container.add(p, key);
        }
        currentType = typePanels.get(0).typeName();
    }

    public JComboBox<String> createTypeCombo() {
        JComboBox<String> combo = new JComboBox<>(panels.keySet().toArray(new String[0]));
        combo.setSelectedItem(currentType);
        combo.addActionListener(e -> show((String) combo.getSelectedItem()));
        return combo;
    }

    public void show(String typeName) {
        if (!panels.containsKey(typeName)) {
            throw new IllegalArgumentException("Loại tài liệu không hỗ trợ: " + typeName);
        }
        cards.show(container, typeName);
        currentType = typeName;
    }

    public TypeFieldPanel current() {
        return panels.get(currentType);
    }

    public JPanel getContainer() {
        return container;
    }
}
