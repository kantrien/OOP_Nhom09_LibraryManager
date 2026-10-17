package vn.edu.ptit.oop.nhom09;

import javax.swing.SwingUtilities;
import vn.edu.ptit.oop.nhom09.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}


