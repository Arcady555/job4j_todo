package ru.job4j.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;

    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Task> findDone(boolean b) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Task as t where t.done = :fB")
                .setParameter("fB", b)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Task add(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        task.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public boolean replace(int id, Task task) {
        task.setId(id);
        Session session = sf.openSession();
        session.beginTransaction();
        task.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        session.update(task);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean replaceDone(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.createQuery(
                "UPDATE Task as i set i.done = true where ID = :fId", Task.class)
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = new Task();
        task.setId(id);
        session.delete(task);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.createQuery(
                        "from Task as i where i.id = :fId", Task.class)
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}