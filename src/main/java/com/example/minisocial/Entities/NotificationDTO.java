package com.example.minisocial.Entities;

import java.io.Serializable;

public class NotificationDTO implements Serializable {
    private String eventType;
    private String message;
    private String author;

    public NotificationDTO(Notification notification) {
        this.eventType = notification.getEventType();
        this.message = notification.getMessage();
        this.author = notification.getRecipient().getUsername();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
