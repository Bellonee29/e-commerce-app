package com.waystech.authmanagement.emailNotification.events;

import com.waystech.authmanagement.Utils.DateUtils;
import com.waystech.authmanagement.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


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
