package com.snapppay.tasks.expensetracker.domain.services;

import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(UserEntity user, String message) {
        // Example: Send an email or push notification
        System.out.println("Sending notification to " + user.getEmail() + ": " + message);
        // Implement actual notification logic here
    }
}
