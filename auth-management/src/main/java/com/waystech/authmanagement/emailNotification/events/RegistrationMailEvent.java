package com.waystech.authmanagement.emailNotification.events;


import com.waystech.authmanagement.user.models.User;

public class RegistrationMailEvent extends Event {

    public RegistrationMailEvent(String eventName, User createdBy, Object eventDetails) {
        super(eventName, createdBy, eventDetails);
    }
}
