package com.example.spring313.service;


import com.example.spring313.dao.UserDao;
import com.example.spring313.model.Role;
import com.example.spring313.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder;


    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }


    @PostConstruct
    public void init() {
        //Создание ролей
        String[] roleNames = {"ROLE_ADMIN","ROLE_USER"};
        List<Role> listRoles = new ArrayList<>();
        for (String name:roleNames) {
            Role role = new Role();
            role.setRole(name);
            listRoles.add(role);
        }

        //Создание пользователя
        User defaultUser = new User();
        defaultUser.setFirstName("Admin");
        defaultUser.setLastName("Admin");
        defaultUser.setEmail("admin");
        defaultUser.setAge("29");
        defaultUser.setPassword(encoder.encode("admin"));
        defaultUser.setCollectionsRoles(listRoles);
        userDao.addUser(defaultUser);
    }

    @Override
    public void addUser(User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        String pass = user.getPassword();
        if (pass.isEmpty()) {
            String password = userDao.findUserById(user.getId()).getPassword();
            user.setPassword(password);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }


}
