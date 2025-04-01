package com.waystech.authmanagement.emailNotification.services;

import com.google.gson.Gson;
import com.waystech.authmanagement.emailNotification.events.*;
import com.waystech.authmanagement.exceptions.classes.UserNotFoundException;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailEventHandlerImpl {
    private static final Gson gson = new Gson();
    private final RegistrationMailHandler registrationMailHandler;
    private final EventCreatedMailHandler eventCreatedMailHandler;
    private final WishlistAddedMailHandler wishlistAddedMailHandler;
    private final UserRepository userRepository;

    @RabbitListener(queues = "user.email.registration", concurrency = "5")
    public void handleRegistrationEmail(String userDetails){
        log.info("Email Details Obtained: {}", userDetails);
        EmailEvent emailEvent = gson.fromJson(userDetails, EmailEvent.class);
        User user = userRepository.findById(emailEvent.getUser().getUserId())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        RegistrationMailEvent mailEvent = new RegistrationMailEvent(emailEvent.getSubject(),user, emailEvent);
        log.info("Registration Mail Model: {}", mailEvent);
        EventBus.registerHandler(mailEvent,registrationMailHandler);
        EventBus.raiseEvents(mailEvent);
    }

    @RabbitListener(queues = "user.email.forgot.password", concurrency = "5")
    public void handleForgotPasswordEmail(String userDetails){
        log.info("Email Details Obtained: {}", userDetails);
        EmailEvent emailEvent = gson.fromJson(userDetails, EmailEvent.class);
        User user = userRepository.findById(emailEvent.getUser().getUserId())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        RegistrationMailEvent mailEvent = new RegistrationMailEvent(emailEvent.getSubject(),user, emailEvent);
        log.info("Registration Mail Model: {}", mailEvent);
        EventBus.registerHandler(mailEvent,registrationMailHandler);
        EventBus.raiseEvents(mailEvent);
    }
    @RabbitListener(queues = "user.email.resend", concurrency = "5")
    public void handleResendOtpEmail(String userDetails){
        log.info("Email Details Obtained: {}", userDetails);
        EmailEvent emailEvent = gson.fromJson(userDetails, EmailEvent.class);
        User user = userRepository.findById(emailEvent.getUser().getUserId())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        RegistrationMailEvent mailEvent = new RegistrationMailEvent(emailEvent.getSubject(),user, emailEvent);
        log.info("Registration Mail Model: {}", mailEvent);
        EventBus.registerHandler(mailEvent,registrationMailHandler);
        EventBus.raiseEvents(mailEvent);
    }

    @RabbitListener(queues = "user.email.event.created", concurrency = "5")
    public void handleEventCreatedMail(String emailDetails){
        log.info("Email Details Obtained: {}", emailDetails);
        EventEmailObject emailObject = gson.fromJson(emailDetails, EventEmailObject.class);
        EventCreatedMailEvent eventCreatedMailEvent = new EventCreatedMailEvent(EventSubjects.EVENT_CREATED.name(),null,emailObject);
        log.info("Registration Mail Model: {}", emailObject);
        EventBus.registerHandler(eventCreatedMailEvent, eventCreatedMailHandler);
        EventBus.raiseEvents(eventCreatedMailEvent);
    }

    @RabbitListener(queues = "user.email.wishlist", concurrency = "5")
    public void handleWishlistMail(String userDetails){
        log.info("Email Details Obtained: {}", userDetails);
        EmailEvent emailEvent = gson.fromJson(userDetails, EmailEvent.class);
        WishListAddedMailEvent wishListAddedMailEvent = new WishListAddedMailEvent(emailEvent.getSubject(),null, emailEvent);
        log.info("Registration Mail Model: {}", wishListAddedMailEvent);
        EventBus.registerHandler(wishListAddedMailEvent,wishlistAddedMailHandler);
        EventBus.raiseEvents(wishListAddedMailEvent);
    }

}
