package vn.edu.ptit.oop.nhom09.ui.dialogs;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import vn.edu.ptit.oop.nhom09.ui.dialogs.form.FormUIHelper;

import javax.swing.JDialog;
import java.awt.Frame;

/**
 * Dialog modal dùng chung cho Thêm và Sửa tài liệu.
 * Cách dùng (mẫu 10.6 trong đề):
 * <pre>
 *   ItemFormDialog d = new ItemFormDialog(this, null); // null = Thêm
 *   d.setVisible(true);                                // chặn tới khi đóng
 *   if (d.isConfirmed()) repo.add(d.getResult());
 * </pre>
 */
public class ItemFormDialog extends JDialog {

    /** null = chế độ Thêm; khác null = chế độ Sửa. */
    private final LibraryItem editing;
    private FormUIHelper helper;
    private boolean confirmed;
    private LibraryItem result;

    // TODO: khai báo các ô nhập chung: ID, tiêu đề, tác giả, năm (JSpinner)

    public ItemFormDialog(Frame owner, LibraryItem editing) {
        super(owner, editing == null ? "Thêm tài liệu" : "Sửa tài liệu", true);
        this.editing = editing;
        buildUI();
        pack();
        setLocationRelativeTo(owner);
    }

    /** private: KHÔNG để lớp con override phương thức được gọi từ constructor. */
    private void buildUI() {
        // TODO 1: tạo helper = new FormUIHelper(List.of(new BookFieldPanel(), ...))
        // TODO 2: panel trên = các trường chung + ComboBox loại
        // TODO 3: giữa = helper.getContainer(); dưới = nút Lưu / Hủy
        // TODO 4: Enter = Lưu (getRootPane().setDefaultButton), ESC = Hủy
        // TODO 5: nếu editing != null → fillForEdit()
    }

    private void fillForEdit() {
        // TODO: đổ trường chung; chọn card theo editing.getTypeName(); helper.current().fill(editing)
        // TODO: khóa ComboBox loại và ô ID
    }

    private void onSave() {
        // TODO 1: commitEdit() cho JSpinner năm (bắt ParseException)
        // TODO 2: gom lỗi phần chung + helper.current().validateFields()
        // TODO 3: có lỗi → JOptionPane liệt kê toàn bộ, return
        // TODO 4: result = helper.current().build(new CommonData(...)); bắt InvalidDataException
        // TODO 5: confirmed = true; dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    /** Đối tượng MỚI đã tạo; chỉ có nghĩa khi isConfirmed() == true. */
    public LibraryItem getResult() {
        return result;
    }
}
