package com.example.minisocial;

import com.example.minisocial.Entities.AuthRequest;
import com.example.minisocial.Entities.User;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Stateless
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public void register(User user) {
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        users.put(id, user);
    }

    public void login(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        User user = users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
    }

    public boolean emailExists(String email) {
        return users.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public Optional<User> getById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
}
