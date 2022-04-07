package com.example.spring313.dao;


import com.example.spring313.model.User;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
       entityManager.createQuery("DELETE FROM User WHERE id = :id")
               .setParameter("id", id)
               .executeUpdate();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findUserById(Long id) {
       return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByLogin(String login) {
        User user = entityManager.createQuery("SELECT user from User user where user.Email = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> result = entityManager.createQuery("SELECT user FROM User user").getResultList();
        return result;
    }
}
