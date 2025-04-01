package com.waystech.authmanagement.integrations.asyncService.services.registrationService;

import com.google.gson.Gson;
import com.waystech.authmanagement.emailNotification.events.EmailEvent;
import com.waystech.authmanagement.integrations.asyncService.model.QueueProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncRegistrationServiceImpl implements AsyncRegistrationService {
    private final static Gson gson = new Gson();
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void queueRegistrationMail(EmailEvent emailEvent){
        log.info("Email Event Obtained: {}",emailEvent);
        String emailDetails = gson.toJson(emailEvent);
        try{
            rabbitTemplate.convertAndSend(QueueProperties.REGISTRATION_EXCHANGE.getName(),
                    QueueProperties.REGISTRATION_ROUTE.getName(), emailDetails);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("Message Queued Successfully");
    }
    @Override
    public void queueForgotPasswordMail(EmailEvent emailEvent){
        log.info("Email Event Obtained: {}",emailEvent);
        String emailDetails = gson.toJson(emailEvent);
        try{
            rabbitTemplate.convertAndSend(QueueProperties.REGISTRATION_EXCHANGE.getName(),
                    QueueProperties.FORGOT_PASSWORD_ROUTE.getName(), emailDetails);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("Message Queued Successfully");
    }

    @Override
    public void queueResendOtpMail(EmailEvent emailEvent) {
        log.info("Email Event Obtained: {}",emailEvent);
        String emailDetails = gson.toJson(emailEvent);
        try{
            rabbitTemplate.convertAndSend(QueueProperties.REGISTRATION_EXCHANGE.getName(),
                    QueueProperties.RESEND_OTP_ROUTE.getName(), emailDetails);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("Message Queued Successfully");
    }
}
