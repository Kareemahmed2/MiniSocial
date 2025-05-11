package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_group")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true,name="group_name")
    private String name;
    private String description;
    @ManyToOne
    private User creator;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;
    @ManyToMany
    @JoinTable(
            name = "group_admins",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> admins;
    @OneToMany(mappedBy = "group")
    private List<GroupPost> posts;
    private Boolean isPrivate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void addMember(User user){
        if (this.members == null) {
            this.members = new ArrayList<>();
        }
        this.members.add(user);
    }

    public void removeMember(User user){
        this.members.remove(user);
    }
    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }
    public void addAdmin(User user){
        this.admins.add(user);
        this.members.remove(user);
    }

    public List<GroupPost> getPosts() {
        return posts;
    }

    public void setPosts(List<GroupPost> posts) {
        this.posts = posts;
    }
    public void addPost(GroupPost groupPost){
        this.posts.add(groupPost);
    }
    public void removePost(GroupPost groupPost){
        this.posts.remove(groupPost);
    }
    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}