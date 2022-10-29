package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Task;
import ru.job4j.persistence.TaskStore;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskStore store;

    public List<Task> findAll() {
        return store.findAll();
    }

    public List<Task> findDone() {
        return store.findDone();
    }

    public List<Task> findNew() {
        return store.findNew();
    }

    public Task add(Task task) {
        return store.add(task);
    }

    public boolean replace(int id, Task task) {
        return store.replace(id, task);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public Task findById(int id) {
        return store.findById(id);
    }
}