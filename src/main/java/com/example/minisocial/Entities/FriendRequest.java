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

    private String Sender;

    private String receiver;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
