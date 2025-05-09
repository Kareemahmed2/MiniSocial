package com.example.minisocial.Entities;

import java.io.Serializable;

public class FriendRequestDTO implements Serializable {
    private String receiver;

    public FriendRequestDTO() {}

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
