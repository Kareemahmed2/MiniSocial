package com.example.minisocial.Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "friend_request")
public class FriendRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private User Sender;
    @ManyToOne
    private User receiver;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
