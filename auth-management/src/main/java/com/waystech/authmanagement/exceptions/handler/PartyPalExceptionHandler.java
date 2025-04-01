package com.waystech.authmanagement.exceptions.handler;

import com.waystech.authmanagement.Utils.DateUtils;
import com.waystech.authmanagement.exceptions.ExceptionResponse;
import com.waystech.authmanagement.exceptions.classes.*;
import com.waystech.authmanagement.user.dto.NovaResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PartyPalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> userNotFound
            (UserNotFoundException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> userAlreadyExists
            (UserAlreadyExistException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.CONFLICT.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OtpNotFoundException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> otpNotFound
            (OtpNotFoundException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> otpExpired
            (OtpExpiredException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> invalidCredentials
            (InvalidCredentialsException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> unauthorized
            (UserUnauthorizedException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> imageException
            (ImageUploadException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProfileUpdateException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> profileUpdateException
            (ProfileUpdateException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<NovaResponse<ExceptionResponse>> customValidation
            (CustomValidationException e, HttpServletRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .time(DateUtils.convertLocalDate(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(new NovaResponse<>
                ("Exception encountered", exceptionResponse), HttpStatus.BAD_REQUEST);
    }

}

