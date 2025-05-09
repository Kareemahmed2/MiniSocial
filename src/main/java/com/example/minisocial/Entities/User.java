package com.example.minisocial.Entities;

import jakarta.persistence.*;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true,name="username")
    private String username;
    @Column(unique = true,name="email")
    private String email;
    @Column(name="password")
    private String password;
    private String bio;
    @ManyToMany(fetch=FetchType.EAGER)
    private List<User> friends;
    @ManyToMany(fetch=FetchType.EAGER)
    private List<FriendRequest> friendRequests;
    @ManyToMany(fetch=FetchType.EAGER)
    private List<Group> groups;


    public User() {
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.groups = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User friend) {
        if (this.friends == null) {
            this.friends = new ArrayList<>();
        }
        this.friends.add(friend);
    }

    public List<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }
    public void addFriendRequest(FriendRequest friendRequest) {
        if (this.friendRequests == null) {
            this.friendRequests = new ArrayList<>();
        }
        this.friendRequests.add(friendRequest);
    }
    public void removeFriendRequest(String sender) {
        this.friendRequests.stream().filter(fr -> fr.getSender().equals(sender)).findFirst().ifPresent(this.friendRequests::remove);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}