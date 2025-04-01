package com.waystech.authmanagement.integrations.asyncService.services.registrationService;

import com.waystech.authmanagement.emailNotification.events.EmailEvent;
import org.springframework.stereotype.Service;

@Service
public interface AsyncRegistrationService {
    void queueRegistrationMail(EmailEvent emailEvent);

    void queueForgotPasswordMail(EmailEvent emailEvent);

    void queueResendOtpMail(EmailEvent emailEvent);
}
