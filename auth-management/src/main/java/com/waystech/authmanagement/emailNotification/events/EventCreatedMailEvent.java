package org.partypal.emailNotification.events;

import org.partypal.commonModule.events.Event;
import org.partypal.userManagement.domain.models.User;

public class EventCreatedMailEvent extends Event {

    public EventCreatedMailEvent(String eventName, User createdBy, Object eventDetails) {
        super(eventName, createdBy, eventDetails);
    }
}
