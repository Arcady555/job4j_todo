package ru.job4j.persistence;

import lombok.AllArgsConstructor;

import net.jcip.annotations.ThreadSafe;

import org.springframework.stereotype.Repository;

import ru.job4j.model.Category;
import ru.job4j.model.Task;
import ru.job4j.persistence.repository.CrudRepository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
@ThreadSafe
public class TaskStore {
    private final CrudRepository crudRepository;

    public List<Task> findAll() {
        return crudRepository.query(
                "from Task task join fetch task.priority", /** join fetch task.categories", */
                Task.class
        );
    /**    return crudRepository.query(
                "from Task t join fetch t.priority LEFT JOIN categories_tasks " +
                        "ct on t.id = ct.task_id LEFT JOIN category c ON ct.category_id = c.id;",
                Task.class
        );  */
    }

    public List<Task> findDone(boolean b) {
        return crudRepository.query("from Task as t join fetch t.priority where t.done = :fB", Task.class,
                Map.of("fB", b));
    }

    public Task add(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    public boolean replace(Task task) {
        crudRepository.run(session -> session.merge(task));
        return findById(task.getId()) == task;
    }

    public boolean replaceDone(int id) {
        crudRepository.run("UPDATE Task SET done = 'true' WHERE id = :fId", Map.of("fId", id));
        return findById(id).isDone();
    }

    public boolean delete(int id) {
        Task task = findById(id);
        crudRepository.run(session -> session.delete(task));
        return task == null;
    }

    public Task findById(int id) {
        return crudRepository.get(
                "from Task as t join fetch t.priority where t.id = :fId", Task.class, Map.of("fId", id));
    }
}