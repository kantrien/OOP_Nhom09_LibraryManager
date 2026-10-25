package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.model.Thesis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThesisFieldPanel extends TypeFieldPanel {

    private static final int MAX_LEN = 100;
    private final JTextField txtSupervisor = new JTextField(20);
    private final JTextField txtUniversity = new JTextField(20);

    public ThesisFieldPanel() {
        setBorder(BorderFactory.createTitledBorder("Thông tin luận văn"));
        setLayout(new GridLayout(0, 2,5,5));

        add(new JLabel("người hướng dẫn:"));
        add(txtSupervisor);

        add(new  JLabel("Trường:"));
        add(txtUniversity);
    }

    @Override
    public String typeName() {
        return "Luận văn";
    }

    @Override
    public List<String> validateFields() {
        List<String> errors = new ArrayList<>();
        checkText(txtSupervisor.getText(), "Người hướng dẫn", errors);
        checkText(txtUniversity.getText(), "Trường", errors);
        return errors;
    }

    private static void checkText(String raw, String label, List<String> errors) {
        String value = raw.trim();
        if (value.isEmpty()) {
            errors.add(label + " không được để trống");
        } else if (value.length() > MAX_LEN) {
            errors.add(label + " không được dài quá " + MAX_LEN + " ký tự");
        }
    }

    @Override
    public LibraryItem build(CommonData common) {
        return new Thesis(common.id(), common.title(), common.author(), common.year(), txtSupervisor.getText().trim(), txtUniversity.getText().trim());
    }

    @Override
    public void fill(LibraryItem item) {
        if (!(item instanceof Thesis thesis)) {
            throw new IllegalArgumentException("ThesisFieldPanel chỉ nhận Thesis, nhận được: " + (item == null ? "null" : item.getClass().getSimpleName()));
        }
        txtSupervisor.setText(thesis.getSupervisor());
        txtUniversity.setText(thesis.getUniversity());
    }

    @Override
    public void clear() {
        txtUniversity.setText("");
        txtSupervisor.setText("");
    }
}
