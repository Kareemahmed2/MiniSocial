package com.example.minisocial.Services;

import com.example.minisocial.Entities.*;
import com.example.minisocial.Repositories.DataEngine;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class UserService {
    @Inject
    DataEngine dataEngine;


    public void register(User user) {
        if (dataEngine.emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        dataEngine.save(user);
    }

    public void login(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        User user = dataEngine.findUserByUsername(username);
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
    }

    public List<FriendRequest> getFriendRequests(String username) {
        return dataEngine.getFriendRequests(username);
    }


    public List<UserDTO> getAll() {
        return dataEngine.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
    public List<NotificationDTO> getNotifications(String username) {
        User user = dataEngine.findUserByUsername(username);
        return user.getNotifications().stream()
                .map(NotificationDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> searchUsers(String query) {
        return dataEngine.searchUsersByNameOrEmail(query).stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
    public List<UserDTO> suggestFriends(String username) {
        User user = dataEngine.findUserByUsername(username);
        Set<User> suggestions = new HashSet<>();

        for (User friend : user.getFriends()) {
            for (User friendOfFriend : friend.getFriends()) {
                if (!friendOfFriend.getUsername().equals(username) &&
                        !user.getFriends().contains(friendOfFriend)) {
                    suggestions.add(friendOfFriend);
                }
            }
        }

        return suggestions.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
}
