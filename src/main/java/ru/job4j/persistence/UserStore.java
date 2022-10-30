package ru.job4j.persistence;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
@ThreadSafe
public class UserStore {
    private final SessionFactory sf;

    public Optional<User> add(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return Optional.of(user);
    }

    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> result = session.createQuery(
                        "from User as i where (i.login = :fLogin and i.password = :fPassword)", User.class)
                .setParameter("fLogin", login)
                .setParameter("fPassword", password)
                .uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
