package com.waystech.authmanagement.emailNotification.events;

import com.waystech.authmanagement.user.dto.response.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailEvent{
    private UserDto user;
    private String subject;
    private String emailContent;
    private String otp;
    public EmailEvent(UserDto user, String subject, String emailContent, String otp) {
        this.user = user;
        this.subject = subject;
        this.emailContent = emailContent;
        this.otp = otp;
    }
}
