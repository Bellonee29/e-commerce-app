package com.waystech.authmanagement.Utils;


import com.waystech.authmanagement.eventManagement.common.exception.ParserException;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getExpirationDate(Integer expiration){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expiration);
        return new Date(calendar.getTime().getTime());
    }

    public static String convertLocalDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
        return date.format(formatter);
    }

    public static LocalDate convertToLocalDate(String date){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        }catch(Exception e){
            throw new ParserException("Unable to parse date to localDate");
        }

    }

    public static Date convertToDate(String date) {
        try{
             return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());
        }catch (ParseException e){
            throw new ParserException("Unable to parse date");
        }
    }

    public static Time convertToTime(String time) {
        try {
            LocalTime localTime = LocalTime.parse(time);
            return Time.valueOf(localTime);
        } catch (Exception e) {
            throw new ParserException("Unable to parse time");
        }
    }
}
