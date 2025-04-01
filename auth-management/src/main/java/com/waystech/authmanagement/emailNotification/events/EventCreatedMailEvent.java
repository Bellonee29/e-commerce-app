package com.waystech.authmanagement.emailNotification.events;


import com.waystech.authmanagement.user.models.User;

public class EventCreatedMailEvent extends Event {

    public EventCreatedMailEvent(String eventName, User createdBy, Object eventDetails) {
        super(eventName, createdBy, eventDetails);
    }
}
