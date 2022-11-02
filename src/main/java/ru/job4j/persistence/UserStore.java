package ru.job4j.persistence;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;
import ru.job4j.persistence.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@ThreadSafe
public class UserStore {
    private CrudRepository crudRepository;

    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            rsl = Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
    public Optional<User> findUserByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }
}
