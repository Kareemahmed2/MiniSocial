package com.example.minisocial.Services;

import com.example.minisocial.Entities.Notification;
import com.example.minisocial.Entities.NotificationEvent;
import com.example.minisocial.Repositories.DataEngine;
import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Topic;
import jakarta.persistence.EntityManager;

import javax.swing.text.html.parser.Entity;
import javax.xml.crypto.Data;

@Stateless
public class NotificationProducer {
    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/topic/NotificationTopic")
    private Topic topic;

    @Inject
    DataEngine dataEngine;
    @Asynchronous
    public void sendNotification(NotificationEvent event) {
        try (JMSContext context = connectionFactory.createContext()) {
            ObjectMessage message = context.createObjectMessage(event);
            context.createProducer().send(topic, message);
            Notification notification = new Notification();
            notification.setEventType(event.getEventType());
            notification.setMessage(event.getMessage());
            notification.setRecipient(dataEngine.findUserByUsername(event.getRecipient()));
            dataEngine.sendNotification(notification);
        }
    }
}
