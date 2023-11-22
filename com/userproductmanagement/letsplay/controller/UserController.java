package com.userproductmanagement.letsplay.controller;

import com.userproductmanagement.letsplay.model.User;
import com.userproductmanagement.letsplay.model.UserResponse;
import com.userproductmanagement.letsplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    // Get a list of all users
    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    // Get a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User user) {
        Boolean updatedUser = userService.updateUser(user, id);
        if (updatedUser) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not updated", HttpStatus.NOT_FOUND);
        }
    }


    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Boolean deletedUser = userService.deleteUser(id);
        if (deletedUser) {
            // Deletion was successful, and the deleted User object is returned
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            // Deletion failed, possibly because the user was not found
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
