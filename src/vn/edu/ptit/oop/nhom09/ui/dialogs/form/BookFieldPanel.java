package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import vn.edu.ptit.oop.nhom09.model.Book;
import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.util.ValidationUtil;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BookFieldPanel extends TypeFieldPanel {

    private final JTextField txtIsbn = new JTextField(20);
    private final JSpinner spnPages = new JSpinner(new SpinnerNumberModel(1,1,5000,1));

    public BookFieldPanel() {
        setBorder(BorderFactory.createTitledBorder("Thông tin sách"));
        setLayout(new GridLayout(0,2,5,5));

        add(new JLabel("Mã ISBN:"));
        add(txtIsbn);

        add(new JLabel("Số trang:"));
        add(spnPages);
    }

    @Override
    public String typeName() {
        return "Sách";
    }

    @Override
    public List<String> validateFields() {
        List<String> errors = new ArrayList<>();
        String isbn = txtIsbn.getText().trim();
        if (isbn.isEmpty()) {
            errors.add("ISBN không được để trống!");
        } else if (!ValidationUtil.isValidISBN(isbn)) {
            errors.add("ISBN không hợp lệ (cần 10 hoặc 13 chữ số, sai chữ số để kiểm tra)");
        }

        try {
            spnPages.commitEdit();
        } catch (ParseException e) {
            errors.add("Số trang phải là số nguyên từ 1 đến 5000");
        }
        return errors;
    }

    @Override
    public LibraryItem build(CommonData common) {
        int pages = ((Number) spnPages.getValue()).intValue();
        return new Book(common.id(), common.title(), common.author(), common.year(), txtIsbn.getText().trim(), pages);
    }

    @Override
    public void fill(LibraryItem item) {
        if (!(item instanceof Book book)) {
            throw new IllegalArgumentException("BookFieldPanel chỉ nhận Book, nhận được: " + (item == null ? "null" : item.getClass().getSimpleName()));
        }
        txtIsbn.setText(book.getIsbn());
        spnPages.setValue(book.getPages());
    }

    @Override
    public void clear() {
        txtIsbn.setText("");
        spnPages.setValue(1);
    }
}
