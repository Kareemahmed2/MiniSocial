package com.example.minisocial.Services;

import com.example.minisocial.Entities.AuthRequest;
import com.example.minisocial.Entities.User;
import com.example.minisocial.Repositories.UserRepo;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Stateless
public class UserService {
    @Inject
    UserRepo userRepo;


    public void register(User user) {
        if (userRepo.emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        userRepo.save(user);
    }

    public User login(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        User user = userRepo.findUserByUsername(username);
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
        return user;
    }

    public boolean emailExists(String email) {
        return userRepo.emailExists(email);
    }

    public User getById(Long id) {
        return userRepo.findUserById(id);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }
}
