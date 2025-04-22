package DAO;

import Model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean insertUser(User user) {
        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            entityManager.merge(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean selectUser(String name, String pwd) {
        try {
            User user = entityManager.createQuery(
                    "FROM User WHERE name = :name AND password = :pwd", User.class)
                    .setParameter("name", name)
                    .setParameter("pwd", pwd)
                    .getSingleResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace(); // << 加這行印出例外，看到錯在哪
            return false;
        }
    }

    public boolean checkUserExist(String name) {
        try {
            User user = entityManager.createQuery(
                    "FROM User WHERE name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return user != null;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserByName(String name) {
        try {
            return entityManager.createQuery(
                    "FROM User WHERE name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean resetPassword(String email, String newPwd) {
        try {
            User user = entityManager.createQuery(
                    "FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (user != null) {
                user.setPassword(newPwd);
                entityManager.merge(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}