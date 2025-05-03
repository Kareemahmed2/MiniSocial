package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private User author;
    @ManyToOne
    private Post post;
    private String content;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}