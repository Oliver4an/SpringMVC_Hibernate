package DAO;

import Model.User;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDao {

    public boolean insertUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean selectUser(String name, String pwd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE name = :name AND password = :pwd", User.class);
            query.setParameter("name", name);
            query.setParameter("pwd", pwd);
            return query.uniqueResult() != null;
        }
    }

    public boolean checkUserExist(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE name = :name", User.class);
            query.setParameter("name", name);
            return query.uniqueResult() != null;
        }
    }

    public User getUserByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE name = :name", User.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }

    public boolean resetPassword(String email, String newPwd) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();
            if (user != null) {
                user.setPassword(newPwd);
                session.update(user);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}