package vn.edu.ptit.oop.nhom09.repository;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import java.util.List;

public interface LibraryRepository {
    void add(LibraryItem item);
    boolean remove(String id);
    boolean update(LibraryItem item);
    LibraryItem findById(String id);
    List<LibraryItem> findAll();
    List<LibraryItem> search(String keyword);
}
