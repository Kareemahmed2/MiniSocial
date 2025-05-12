package com.example.minisocial.Services;

import com.example.minisocial.Entities.NotificationEvent;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topic/NotificationTopic"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")
        }
)
public class NotificationConsumer implements MessageListener {
        @Override
        public void onMessage(Message message) {
                try {
                        ObjectMessage objMsg = (ObjectMessage) message;
                        NotificationEvent event = (NotificationEvent) objMsg.getObject();
                        System.out.println("[Notification] " + event.getEventType() + ": " + event.getMessage());
                } catch (JMSException e) {
                        e.printStackTrace();
                }
        }
}
