package ru.job4j.persistence.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@AllArgsConstructor
@ThreadSafe
public class CrudRepository {
    private final SessionFactory sf;

    public void run(Consumer<Session> command) {
        tx(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sq = session
                    .createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    public <T> T get(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, T> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResult();
        };
        return tx(command);
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        Transaction tx;
        try (session) {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            tx = session.getTransaction();
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}