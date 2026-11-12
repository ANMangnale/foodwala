package com.tap.dao;

import com.tap.model.User;
import java.util.List;

public interface UserDao {

    void addUser(User user);

    User getUser(int userId);

    void updateUser(User user);

    void deleteUser(int userId);
    
    List<User> getAllUsers();
    
    User getUserByEmail(String userEmail);
    
    User login(String email, String password);
}