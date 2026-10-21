package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;

/** Trường riêng của Sách: ISBN, số trang. */
public class BookFieldPanel extends TypeFieldPanel {

    private final JTextField txtIsbn = new JTextField(20);
    private final JSpinner spnPages = null; // TODO: new JSpinner(new SpinnerNumberModel(...))

    public BookFieldPanel() {
        // TODO: chọn layout (gợi ý GridBagLayout hoặc GridLayout(0, 2)), thêm JLabel + ô nhập
    }

    @Override
    public String typeName() {
        return "Sách";
    }

    @Override
    public List<String> validateFields() {
        List<String> errors = new ArrayList<>();
        // TODO: ISBN qua ValidationUtil.isValidIsbn(...); số trang > 0
        return errors;
    }

    @Override
    public LibraryItem build(CommonData common) {
        // TODO: return new Book(common.id(), common.title(), ..., isbn, pages);
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void fill(LibraryItem item) {
        // TODO: ép kiểu sang Book (vì sao ở đây ép kiểu là chấp nhận được?) rồi setText/setValue
    }

    @Override
    public void clear() {
        // TODO
    }
}
