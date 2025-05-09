package com.example.minisocial.Entities;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private List<String> friends;

    public UserDTO() {}
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.friends = user.getFriends().stream()
                .map(User::getUsername).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
