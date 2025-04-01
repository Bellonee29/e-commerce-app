package org.partypal.emailNotification.events;

import org.partypal.commonModule.events.Event;
import org.partypal.userManagement.domain.models.User;

public class RegistrationMailEvent extends Event {

    public RegistrationMailEvent(String eventName, User createdBy, Object eventDetails) {
        super(eventName, createdBy, eventDetails);
    }
}
