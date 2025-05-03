package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "group")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    private User creator;
    @ManyToMany
    private Set<User> members;
    @ManyToMany
    private Set<User> admins;
    @OneToMany(mappedBy = "group")
    private List<GroupPost> posts;
    private Boolean isPrivate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}