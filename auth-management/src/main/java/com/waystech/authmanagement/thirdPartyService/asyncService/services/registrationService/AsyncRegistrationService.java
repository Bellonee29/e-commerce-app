package org.partypal.thirdPartyService.asyncService.services.registrationService;

import org.partypal.emailNotification.events.EmailEvent;
import org.springframework.stereotype.Service;

@Service
public interface AsyncRegistrationService {
    void queueRegistrationMail(EmailEvent emailEvent);

    void queueForgotPasswordMail(EmailEvent emailEvent);

    void queueResendOtpMail(EmailEvent emailEvent);
}
