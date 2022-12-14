package ru.job4j.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import ru.job4j.model.Category;
import ru.job4j.persistence.CategoryStore;

import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class CategoryService {
    private final CategoryStore categoryStore;

    public List<Category> findAll() {
        return categoryStore.findAll();
    }

    public List<Category> createList(String str) {
        return categoryStore.createList(str);
    }

    public Category findById(int id) {
        return categoryStore.findById(id);
    }

    public List<Category> findByTaskId(int id) {
        return categoryStore.findByTaskId(id);
    }

    public List<Integer> getCategoryIds(int taskId) {
        return categoryStore.getCategoryIds(taskId);
    }
}
