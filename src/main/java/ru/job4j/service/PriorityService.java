package ru.job4j.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Priority;
import ru.job4j.persistence.PriorityStore;

import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class PriorityService {
    private final PriorityStore priorityStore;

    public List<Priority> findAll() {
        return priorityStore.findAll();
    }
}
