package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
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
    private List<User> likedBy;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getLikedBy() {
        return likedBy;
    }
    public void addLike(User user) {
        if (this.likedBy == null) {
            this.likedBy = new ArrayList<>();
        }
        this.likedBy.add(user);
    }
    public void removeLike(User user) {
        this.likedBy.remove(user);
    }
    public void setLikedBy(List<User> likedBy) {
        this.likedBy = likedBy;
    }
}