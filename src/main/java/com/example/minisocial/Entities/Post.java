package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private User author;
    private String content;
    private String mediaURL;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @ManyToMany
    private Set<User> likedBy;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}