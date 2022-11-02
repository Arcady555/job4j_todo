package ru.job4j.persistence;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
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
        return crudRepository.query("from Task", Task.class);
    }

    public List<Task> findDone(boolean b) {
        return crudRepository.query("from Task as t where t.done = :fB", Task.class,
                Map.of("fB", b));
    }

    public Task add(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    public boolean replace(int id, Task task) {
        task.setId(id);
        crudRepository.run(session -> session.merge(task));
        return findById(id) == task;
    }

    public boolean replaceDone(int id) {
        crudRepository.run("UPDATE Task SET done = 'true' WHERE id = :fId", Map.of("fId", id));
        return findById(id).isDone();
    }

    public boolean delete(int id) {
        crudRepository.run(session -> session.delete(id));
        return findById(id) == null;
    }

    public Task findById(int id) {
        return crudRepository.get(
                "from Task as i where i.id = :fId", Task.class, Map.of("fId", id));
    }
}