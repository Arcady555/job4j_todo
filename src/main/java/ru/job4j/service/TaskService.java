package ru.job4j.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Task;
import ru.job4j.persistence.TaskStore;

import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class TaskService {
    private final TaskStore store;

    public List<Task> findAll() {
        return store.findAll();
    }

    public List<Task> findDone(boolean b) {
        return store.findDone(b);
    }

    public Task add(Task task) {
        return store.add(task);
    }

    public boolean replace(int id, Task task) {
        return store.replace(id, task);
    }

    public void replaceDone(int id) {
        store.replaceDone(id);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public Task findById(int id) {
        return store.findById(id);
    }
}