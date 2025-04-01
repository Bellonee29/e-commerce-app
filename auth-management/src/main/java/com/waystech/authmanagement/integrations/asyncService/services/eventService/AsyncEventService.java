package com.waystech.authmanagement.integrations.asyncService.services.eventService;


import com.waystech.authmanagement.emailNotification.events.EmailEvent;
import com.waystech.authmanagement.emailNotification.events.EventEmailObject;

public interface AsyncEventService {
    void queueEventCreationMail(EventEmailObject eventEmailObject);

    void queueWishlistMail(EmailEvent emailEvent);
}
