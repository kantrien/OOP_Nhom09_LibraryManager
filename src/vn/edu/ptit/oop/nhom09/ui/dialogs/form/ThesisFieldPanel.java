package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;

import java.util.ArrayList;
import java.util.List;

/** Trường riêng của Luận văn: người hướng dẫn (supervisor), trường (university). */
public class ThesisFieldPanel extends TypeFieldPanel {

    // TODO: khai báo các ô nhập (private final)

    public ThesisFieldPanel() {
        // TODO: layout + JLabel + ô nhập
    }

    @Override
    public String typeName() {
        return "Luận văn";
    }

    @Override
    public List<String> validateFields() {
        List<String> errors = new ArrayList<>();
        // TODO
        return errors;
    }

    @Override
    public LibraryItem build(CommonData common) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void fill(LibraryItem item) {
        // TODO
    }

    @Override
    public void clear() {
        // TODO
    }
}
