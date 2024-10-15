package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id bigint auto_increment PRIMARY KEY, " +
                "name VARCHAR(60) NULL, lastName VARCHAR(60) NULL, " +
                "age TINYINT NULL)";

        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(session.get(User.class, id));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        String sql = "SELECT * FROM users";
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                users = session.createSQLQuery(sql).addEntity(User.class).getResultList();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
