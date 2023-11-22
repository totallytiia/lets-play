package com.userproductmanagement.letsplay.service.impl;

import com.userproductmanagement.letsplay.exception.EntityNotFoundException;
import com.userproductmanagement.letsplay.model.Role;
import com.userproductmanagement.letsplay.model.User;
import com.userproductmanagement.letsplay.model.UserResponse;
import com.userproductmanagement.letsplay.repository.UserRepository;
import com.userproductmanagement.letsplay.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${admin.default.name}")
    private String adminName;

    @Value("${admin.default.email}")
    private String adminEmail;

    @Value("${admin.default.password}")
    private String adminPassword;

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToUserResponse(User user) {
        User authenticatedUser = getAuthenticatedUser();

        UserResponse.UserResponseBuilder responseBuilder = UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .id(user.getId());

        if (authenticatedUser != null) {
            boolean isAdmin = authenticatedUser.getRole().equals(Role.ROLE_ADMIN);
            boolean isSelf = user.getId().equals(authenticatedUser.getId());

            if (isAdmin || isSelf) {
                responseBuilder.id(user.getId());
            }
        }

        return responseBuilder.build();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return null;
        }

        return userRepository.findById(userDetails.getUsername())
                .orElse(null);
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User is not found with id: " + id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Boolean deleteUser(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Boolean updateUser(User userUpdateReq, String id) {
        if (userUpdateReq.getEmail() == null && userUpdateReq.getPassword() == null && userUpdateReq.getName() == null && userUpdateReq.getRole() ==  null) {
            throw new InvalidParameterException("invalid request");
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (userUpdateReq.getName() != null) {
                        existingUser.setName(userUpdateReq.getName());
                    }
                    if (userUpdateReq.getEmail() != null) {
                        existingUser.setEmail(userUpdateReq.getEmail());
                    }
                    if (userUpdateReq.getPassword() != null) {
                        existingUser.setPassword((userUpdateReq.getPassword()));
                    }
                    if (userUpdateReq.getRole() != null) {
                        existingUser.setRole(userUpdateReq.getRole());
                    }
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }

    @PostConstruct
    public void initialDefaultAdmin() {
        // Check if the default admin exists in the DB
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .name(adminName)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }
}
