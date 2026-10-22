package vn.edu.ptit.oop.nhom09.repository;

import vn.edu.ptit.oop.nhom09.model.LibraryItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryLibraryRepository implements LibraryRepository {
    
    private final Map<String, LibraryItem> itemsMap = new HashMap<>();

    public InMemoryLibraryRepository() {
       
        MockDataGenerator.generateSampleData(this);
    }

    @Override
    public void add(LibraryItem item) {
        if (item != null && item.getId() != null) {
            itemsMap.put(item.getId(), item);
        }
    }

    @Override
    public boolean remove(String id) {
        return itemsMap.remove(id) != null;
    }

    @Override
    public boolean update(LibraryItem item) {
        if (item != null && itemsMap.containsKey(item.getId())) {
            itemsMap.put(item.getId(), item);
            return true;
        }
        return false;
    }

    @Override
    public LibraryItem findById(String id) {
        return itemsMap.get(id);
    }

    @Override
    public List<LibraryItem> findAll() {
       
        return new ArrayList<>(itemsMap.values());
    }

    @Override
    public List<LibraryItem> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lowerKw = keyword.trim().toLowerCase();
        return itemsMap.values().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(lowerKw) ||
                                item.getAuthor().toLowerCase().contains(lowerKw) ||
                                item.getId().toLowerCase().contains(lowerKw))
                .collect(Collectors.toList());
    }
}