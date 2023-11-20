package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User getUser(String id);

    Optional<User> getUserByEmail(String email);

    Boolean deleteUser(String id);

    Boolean updateUser(User user, String id);

}
