package app.dao;

import app.exception.DBException;
import app.model.User;
import org.hibernate.*;


import java.util.List;

public class UsersDaoHibernate implements UsersDao {

    private final SessionFactory sessionFactory;

    public UsersDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long getUserId(String login) throws DBException {
        long id = -1;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "from User u where u.login = :login";
            User user = (User) session.createQuery(hql)
                    .setParameter("login", login)
                    .uniqueResult();
            if (user != null){
                id = user.getId();
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void addUser(User user) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserByLogin(String login) throws DBException {
        User user = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "from User u where u.login = :login";
            user = (User) session.createQuery(hql)
                    .setParameter("login", login)
                    .uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public User getUserById(long id) throws DBException {
        User user = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            user = (User) session.get(User.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void deleteUser(long id) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateUser(User user) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() throws DBException {
        List<User> list = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            list = (List<User>) session.createQuery("from User").list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DBException(e);
        } finally {
            session.close();
        }
        return list;
    }
}
