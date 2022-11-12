package ru.job4j.persistence;

import lombok.AllArgsConstructor;

import net.jcip.annotations.ThreadSafe;

import org.springframework.stereotype.Repository;

import ru.job4j.model.Category;
import ru.job4j.persistence.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
@ThreadSafe
public class CategoryStore {
    private final CrudRepository crudRepository;

    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }
    public Category findById(int id) {
        return crudRepository.get(
                "from Category c where c.id = :fId", Category.class, Map.of("fId", id));
    }

    public List<Category> createList(String str) {
        List<Category> categoryList = new ArrayList<>();
        for (String element : str.split(",")) {
            categoryList.add(findByName(element));
        }
        return categoryList;
    }

    public List<Category> findByTaskId(int id) {
        List<Integer> categoryIdList = crudRepository.query(
                "SELECT category_id FROM categories_tasks AS c WHERE c.task_id = = :fId",
                Integer.class,
                Map.of("fId", id)
        );
        List<Category> categories = new ArrayList<>();
        for (int categoryId : categoryIdList) {
            categories.add(findById(categoryId));
        }
        return categories;
    }

    public List<Integer> getCategoryIds(int taskId) {
        return crudRepository.query(
                "SELECT category_id FROM categories_tasks AS c WHERE c.task_id = = :fId",
                Integer.class,
                Map.of("fId", taskId)
        );
    }

    private Category findByName(String name) {
        return crudRepository.get(
                "from Category c where c.name = :fId", Category.class, Map.of("fId", name));
    }
}
