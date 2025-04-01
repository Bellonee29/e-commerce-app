package com.waystech.authmanagement.emailNotification.events;


import com.waystech.authmanagement.user.models.User;

public class WishListAddedMailEvent extends Event {

    public WishListAddedMailEvent(String eventName, User createdBy, Object eventDetails) {
        super(eventName, createdBy, eventDetails);
    }
}
