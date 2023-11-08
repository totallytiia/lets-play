package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUsers();

    public User getUser(String id);

    public User addUser(User user);

    public User deleteUser(String id);

    public User updateUser(User user, String id);

}
