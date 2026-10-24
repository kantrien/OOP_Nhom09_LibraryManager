package vn.edu.ptit.oop.nhom09.ui.dialogs.form;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;

import javax.swing.JPanel;
import java.util.List;

/**
 * Panel chứa các trường RIÊNG của một loại tài liệu.
 * Mỗi lớp con biết cách validate, tạo đối tượng và đổ dữ liệu cho đúng loại của mình,
 * nhờ đó ItemFormDialog không cần if/switch theo loại (đa hình thay cho instanceof).
 */
public abstract class TypeFieldPanel extends JPanel {

    /** Khóa card; PHẢI trùng với LibraryItem.getTypeName() của lớp tương ứng. */
    public abstract String typeName();

    /**
     * Kiểm tra các trường riêng.
     * @return danh sách thông báo lỗi; rỗng nghĩa là hợp lệ
     */
    public abstract List<String> validateFields();

    /** Tạo một đối tượng MỚI từ phần dữ liệu chung + các trường riêng của panel này. */
    public abstract LibraryItem build(CommonData common);

    /** Đổ dữ liệu của item vào các ô nhập (chế độ Sửa). */
    public abstract void fill(LibraryItem item);

    /** Xóa trắng các ô nhập. */
    public abstract void clear();
}
