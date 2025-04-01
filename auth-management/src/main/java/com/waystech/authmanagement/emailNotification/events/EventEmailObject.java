package org.partypal.emailNotification.events;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class EventEmailObject {
    private String subject;
    private String emailContent;
    private List<String> userEmails;

    public EventEmailObject(String subject, String emailContent, List<String> userEmails) {
        this.subject = subject;
        this.emailContent = emailContent;
        this.userEmails = userEmails;
    }
}
