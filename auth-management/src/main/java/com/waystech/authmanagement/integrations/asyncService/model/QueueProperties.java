package com.waystech.authmanagement.integrations.asyncService.model;

public enum QueueProperties {
    // Exchanges
    REGISTRATION_EXCHANGE("user.registration"),

    // Queues
    REGISTRATION_EMAIL_QUEUE("user.email.registration"),
    FORGOT_PASSWORD_QUEUE("user.email.forgot.password"),
    RESEND_OTP_QUEUE("user.email.resend"),
    EVENT_CREATION_QUEUE("user.email.event.created"),
    WISHLIST_QUEUE("user.email.wishlist"),

    // Routes
    FORGOT_PASSWORD_ROUTE("forgotPassword"),
    REGISTRATION_ROUTE("registration"),
    RESEND_OTP_ROUTE("resendOTP"),
    EVENT_CREATION_ROUTE("eventCreated"),
    WISHLIST_ROUTE("wishlistAdded");

    private final String names;

    QueueProperties(String names){
        this.names = names;
    }

    public String getName(){
        return names;
    }
}
