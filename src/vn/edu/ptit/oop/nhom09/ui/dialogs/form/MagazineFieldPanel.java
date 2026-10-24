package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.model.Magazine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MagazineFieldPanel extends TypeFieldPanel {

    private static final int MAX_ISSUE = 10_000;

    private final JSpinner spnIssue = new JSpinner(new SpinnerNumberModel(1, 1, MAX_ISSUE, 1));

    public MagazineFieldPanel() {
        setBorder(BorderFactory.createTitledBorder("Thông tin tạp chí"));
        setLayout(new GridLayout(0,2,5,5));

        add(new JLabel("Số phát hành"));
        add(spnIssue);
    }

    @Override
    public String typeName() {
        return "Tạp chí";
    }

    @Override
    public List<String> validateFields() {
        List<String> errors = new ArrayList<>();
        try {
            spnIssue.commitEdit();
        } catch (Exception ex) {
            errors.add("Số phát hành phải là số nguyên từ 1 đến " + MAX_ISSUE);
        }
        return errors;
    }

    @Override
    public LibraryItem build(CommonData common) {
        int issue = ((Number) spnIssue.getValue()).intValue();
        return new Magazine(common.id(), common.title(), common.author(), common.year(), issue);
    }

    @Override
    public void fill(LibraryItem item) {
        if (! (item instanceof Magazine magazine)) {
            throw new IllegalArgumentException("MagazineFieldPanel chỉ nhận Magazine, nhận được: " + (item == null ? "null" : item.getClass().getSimpleName()));
        }
        spnIssue.setValue(magazine.getIssueNumber());
    }

    @Override
    public void clear() {
        spnIssue.setValue(1);
    }
}
