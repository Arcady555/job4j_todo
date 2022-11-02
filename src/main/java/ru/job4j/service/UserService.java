package ru.job4j.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.persistence.UserStore;

import java.util.Optional;

@Service
@AllArgsConstructor
@ThreadSafe
public class UserService {
    private UserStore store;

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByLogin(String login) {
        return store.findUserByLogin(login);
    }
}
