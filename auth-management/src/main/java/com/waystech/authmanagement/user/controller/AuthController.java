package com.waystech.authmanagement.user.controller;

import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.ForgotPasswordRequest;
import com.waystech.authmanagement.user.dto.request.SignInRequest;
import com.waystech.authmanagement.user.dto.request.SignUpRequest;
import com.waystech.authmanagement.user.dto.request.VerificationRequest;
import com.waystech.authmanagement.user.dto.response.ProfileResponse;
import com.waystech.authmanagement.user.dto.response.SignInResponse;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import com.waystech.authmanagement.user.services.loginService.LoginService;
import com.waystech.authmanagement.user.services.passwordService.PasswordService;
import com.waystech.authmanagement.user.services.profileService.ProfileService;
import com.waystech.authmanagement.user.services.signupService.SignUpService;
import com.waystech.authmanagement.user.services.verificationService.VerificationService;
import com.waystech.authmanagement.validations.ImageFileValidator;
import com.waystech.authmanagement.validations.UsernameValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final SignUpService signUpService;
    private final VerificationService verificationService;
    private final LoginService loginService;
    private final PasswordService passwordService;
    private final ProfileService profileService;
    private final UsernameValidator usernameValidator;

    @PostMapping("/register-user")
    public ResponseEntity<NovaResponse<SignUpResponse>> registerUser(@Valid @RequestBody SignUpRequest request){
        return new ResponseEntity<>(signUpService.registerUser(request), HttpStatus.CREATED);
    }

    @PatchMapping("/verify-otp")
    public ResponseEntity<NovaResponse<?>> verifyUser(@Valid @RequestBody VerificationRequest request,
                                                          @RequestParam("purpose") String purpose){
        if(purpose.equals("registration")){
            return new ResponseEntity<>(verificationService.verifyRegistrationOTP(request), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(verificationService.verifyPasswordOTP(request), HttpStatus.OK);
        }

    }

    @GetMapping("/resend-otp")
    public ResponseEntity<NovaResponse<String>> resendRegistrationVerification(@RequestParam("email") String email,
                                                                                   @RequestParam("purpose") String purpose){
        if(purpose.equals("registration")){
            return new ResponseEntity<>(verificationService.resendRegistrationOTP(email), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(verificationService.resendPasswordOTP(email), HttpStatus.OK);
        }

    }

    @PostMapping("/login-user")
    public ResponseEntity<NovaResponse<SignInResponse>> loginUser(@Valid @RequestBody SignInRequest request){
        return new ResponseEntity<>(loginService.loginUser(request), HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<NovaResponse<SignUpResponse>> forgotPassword(@RequestParam("email") String email){
        return new ResponseEntity<>(passwordService.forgotPassword(email), HttpStatus.OK);
    }
    @PatchMapping("/reset-password")
    public ResponseEntity<NovaResponse<String>> resetPassword(@Valid @RequestBody ForgotPasswordRequest request){
        return new ResponseEntity<>(passwordService.resetPassword(request), HttpStatus.OK);
    }

    @PatchMapping(value = "/upload-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NovaResponse<String>> uploadProfile(@RequestParam("image") MultipartFile image,
                                                                  @RequestParam("username") String username,
                                                                  @RequestParam("location") String location){
        usernameValidator.checkValidity(username);
        ImageFileValidator.isValid(image);
        return new ResponseEntity<>(profileService.uploadProfile(image, username, location), HttpStatus.OK);
    }

    @PutMapping(value = "/edit-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NovaResponse<ProfileResponse>> editProfile(@RequestParam("image") MultipartFile image,
                                                                         @RequestParam("details") String details){
        ImageFileValidator.isValid(image);
        return new ResponseEntity<>(profileService.editProfile(image, details), HttpStatus.OK);
    }

    @GetMapping("/get-user-profile")
    public ResponseEntity<NovaResponse<ProfileResponse>> getLoggedProfile(){
        return new ResponseEntity<>(profileService.getLoggedInProfile(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user-profile")
    public ResponseEntity<NovaResponse<String>> deleteUserAccount(){
        return new ResponseEntity<>(profileService.deleteAccount(), HttpStatus.OK);
    }
}
