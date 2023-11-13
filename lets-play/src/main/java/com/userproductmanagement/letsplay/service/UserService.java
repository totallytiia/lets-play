package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String id);

    User addUser(User user);

    User deleteUser(String id);

    User updateUser(User user, String id);

}
