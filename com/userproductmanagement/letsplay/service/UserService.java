package com.userproductmanagement.letsplay.service;

import com.userproductmanagement.letsplay.model.User;
import com.userproductmanagement.letsplay.model.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> getUsers();

    User getUser(String id);

    Optional<User> getUserByEmail(String email);

    Boolean deleteUser(String id);

    Boolean updateUser(User user, String id);

}
