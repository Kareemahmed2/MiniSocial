package com.example.minisocial.Entities;

import jakarta.annotation.Resource;
import jakarta.jms.Topic;

import java.io.Serializable;

public class NotificationEvent implements Serializable {
    private String eventType;
    private String author;
    private String recipient;
    private String message;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "NotificationEvent{" +
                "eventType='" + eventType + '\'' +
                ", author='" + author + '\'' +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
