package com.waystech.authmanagement.emailNotification.events;

import org.springframework.stereotype.Service;

@Service
public interface EventHandler {
    void handleEvent(Event event);
}
