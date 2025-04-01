package org.partypal.emailNotification.models;

import org.partypal.eventManagement.application.dto.response.EventResponse;

public class EmailModels {

    public static  String OTP_REGISTRATION(String firstname, String senderName, String otp){
        return "<div style='width:100%; background: #f8f8f8;' >"
                + "<p style='font-size: 18px;'>Hello, " + firstname + "</p>"
                + "<p style='font-size: 16px;'>" + "Welcome to "+senderName+" </p>"
                + "<p style='font-size: 16px;'></p>"
                + "<p style='font-size: 16px;'>Your OTP to complete your registration is:</p>"
                + "<h1 style='font-size: 24px; margin: 20px 0;'>" + otp + "</h1>"
                + "<p style='font-size: 16px;'>Thank you for registering with us.</p>"
                + "</div>";
    }

    public static String OTP_PASSWORD(String firstname, String otp){
        return "<div style='width:100%; background: #f8f8f8;' >"
                + "<p style='font-size: 18px;'>Hello, " + firstname + "</p>"
                + "<p style='font-size: 16px;'></p>"
                + "<p style='font-size: 16px;'>Your OTP to reset your password is:</p>"
                + "<h1 style='font-size: 24px; margin: 20px 0;'>" + otp + "</h1>"
                + "<p style='font-size: 16px;'>Thank you for joining PARTY PAL.</p>"
                + "</div>";
    }

    public static String EVENT_CREATED_FAVORITE_PLACES(EventResponse event){
        return "<div style='width:100%; background: #f8f8f8;' >"
                + "<p style='font-size: 18px;'>Hello</p>"
                + "<p style='font-size: 16px;'>Check out an event happening near you</p>"
                + "<h1 style='font-size: 16px; margin: 20px 0;'>" + event.getName()+ "</h1>"
                + "<p style='font-size: 16px;'>Happening on the "+ event.getStartDate()+", "+event.getStartTime() +"</p>"
                + "<p style='font-size: 16px;'>Live at "+event.getVenue().getName()+"</p>"
                + "<p style='font-size: 16px;'>Find out more @ www.event@partypal.com</p>"
                + "</div>";
    }

}
