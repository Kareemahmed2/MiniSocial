package com.example.minisocial.Services;

import com.example.minisocial.Entities.AuthRequest;
import com.example.minisocial.Entities.FriendRequest;
import com.example.minisocial.Entities.User;
import com.example.minisocial.Entities.UserDTO;
import com.example.minisocial.Repositories.UserRepo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

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

    public void login(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        User user = userRepo.findUserByUsername(username);
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
    }

    public List<FriendRequest> getFriendRequests(String username) {
        return userRepo.getFriendRequests(username);
    }


    public List<UserDTO> getAll() {
        return userRepo.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

}
