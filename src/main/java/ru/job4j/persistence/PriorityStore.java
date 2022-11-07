package ru.job4j.persistence;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Priority;
import ru.job4j.persistence.repository.CrudRepository;

import java.util.List;

@Repository
@AllArgsConstructor
@ThreadSafe
public class PriorityStore {
    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query("from Priority", Priority.class);
    }
}