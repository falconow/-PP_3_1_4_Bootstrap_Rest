package com.example.spring313.dao;



import com.example.spring313.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void deleteUser(Long id);
    void updateUser(User user);
    User findUserById(Long id);
    User findUserByLogin(String login);
    List<User> getAllUser();
}
