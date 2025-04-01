package org.partypal.commonModule.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.partypal.commonModule.utils.DateUtils;
import org.partypal.userManagement.domain.models.User;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
abstract public class Event {
    private final String id;
    private final String eventName;
    private final String createdAt;
    private final Object eventDetails;
    private final User createdBy;

    public Event(String eventName, User createdBy, Object eventDetails){
        this.id = UUID.randomUUID().toString();
        this.eventName = eventName;
        this.createdAt = DateUtils.convertLocalDate(LocalDateTime.now());
        this.createdBy = createdBy;
        this.eventDetails = eventDetails;
    }
}
