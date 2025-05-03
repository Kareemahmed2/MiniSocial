package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "group_post")
public class GroupPost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private User author;
    private String content;
    private String mediaURL;
    @ManyToOne
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}