package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String id);

    User addUser(User user);

    Boolean deleteUser(String id);

    Boolean updateUser(User user, String id);

}
