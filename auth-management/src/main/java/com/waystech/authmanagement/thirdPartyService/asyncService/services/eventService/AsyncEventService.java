package org.partypal.thirdPartyService.asyncService.services.eventService;

import org.partypal.emailNotification.events.EmailEvent;
import org.partypal.emailNotification.events.EventEmailObject;

public interface AsyncEventService {
    void queueEventCreationMail(EventEmailObject eventEmailObject);

    void queueWishlistMail(EmailEvent emailEvent);
}
