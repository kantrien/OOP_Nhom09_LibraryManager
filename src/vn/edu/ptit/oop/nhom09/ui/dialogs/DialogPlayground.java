package vn.edu.ptit.oop.nhom09.ui.dialogs;

import vn.edu.ptit.oop.nhom09.model.Book;

import javax.swing.SwingUtilities;

/** Chạy thử dialog độc lập, không cần MainWindow. KHÔNG merge file này vào main. */
public class DialogPlayground {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Chế độ Thêm
            ItemFormDialog add = new ItemFormDialog(null, null);
            add.setVisible(true);
            System.out.println("Thêm: " + add.isConfirmed() + " → " + add.getResult());

            // Chế độ Sửa
            Book sample = new Book("B001", "Lập trình Java Nâng cao", "Nguyễn Văn A",
                    2021, "978-604-0-12345-1", 450);
            ItemFormDialog edit = new ItemFormDialog(null, sample);
            edit.setVisible(true);
            System.out.println("Sửa: " + edit.isConfirmed() + " → " + edit.getResult());
        });
    }
}
