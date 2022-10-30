package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.persistence.UserStore;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserStore store;

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        return store.findUserByLoginAndPwd(login, password);
    }
}
