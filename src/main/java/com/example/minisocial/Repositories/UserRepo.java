package com.example.minisocial.Repositories;

import com.example.minisocial.Entities.User;
import jakarta.ejb.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class UserRepo {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public User save(User user) {
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public User findUserByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User findUserById(Long id) {
        return users.get(id);
    }

    public boolean emailExists(String email) {
        return users.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public List<User> findAll() {return new ArrayList<>(users.values());}
}
