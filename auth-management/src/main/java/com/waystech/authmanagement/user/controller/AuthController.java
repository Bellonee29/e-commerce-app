package com.waystech.authmanagement.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.commonModule.validations.ImageFileValidator;
import org.partypal.commonModule.validations.UsernameValidator;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.*;
import org.partypal.userManagement.application.dto.response.ProfileResponse;
import org.partypal.userManagement.application.dto.response.SignInResponse;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.partypal.userManagement.domain.services.loginService.LoginService;
import org.partypal.userManagement.domain.services.passwordService.PasswordService;
import org.partypal.userManagement.domain.services.profileService.ProfileService;
import org.partypal.userManagement.domain.services.signupService.SignUpService;
import org.partypal.userManagement.domain.services.verificationService.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class OnboardingController {

    private final SignUpService signUpService;
    private final VerificationService verificationService;
    private final LoginService loginService;
    private final PasswordService passwordService;
    private final ProfileService profileService;
    private final UsernameValidator usernameValidator;

    @PostMapping("/register-user")
    public ResponseEntity<PartyPalResponse<SignUpResponse>> registerUser(@Valid @RequestBody SignUpRequest request){
        return new ResponseEntity<>(signUpService.registerUser(request), HttpStatus.CREATED);
    }

    @PatchMapping("/verify-otp")
    public ResponseEntity<PartyPalResponse<?>> verifyUser(@Valid @RequestBody VerificationRequest request,
                                                          @RequestParam("purpose") String purpose){
        if(purpose.equals("registration")){
            return new ResponseEntity<>(verificationService.verifyRegistrationOTP(request), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(verificationService.verifyPasswordOTP(request), HttpStatus.OK);
        }

    }

    @GetMapping("/resend-otp")
    public ResponseEntity<PartyPalResponse<String>> resendRegistrationVerification(@RequestParam("email") String email,
                                                                                   @RequestParam("purpose") String purpose){
        if(purpose.equals("registration")){
            return new ResponseEntity<>(verificationService.resendRegistrationOTP(email), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(verificationService.resendPasswordOTP(email), HttpStatus.OK);
        }

    }

    @PostMapping("/login-user")
    public ResponseEntity<PartyPalResponse<SignInResponse>> loginUser(@Valid @RequestBody SignInRequest request){
        return new ResponseEntity<>(loginService.loginUser(request), HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<PartyPalResponse<SignUpResponse>> forgotPassword(@RequestParam("email") String email){
        return new ResponseEntity<>(passwordService.forgotPassword(email), HttpStatus.OK);
    }
    @PatchMapping("/reset-password")
    public ResponseEntity<PartyPalResponse<String>> resetPassword(@Valid @RequestBody ForgotPasswordRequest request){
        return new ResponseEntity<>(passwordService.resetPassword(request), HttpStatus.OK);
    }

    @PatchMapping(value = "/upload-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PartyPalResponse<String>> uploadProfile(@RequestParam("image") MultipartFile image,
                                                                  @RequestParam("username") String username,
                                                                  @RequestParam("location") String location){
        usernameValidator.checkValidity(username);
        ImageFileValidator.isValid(image);
        return new ResponseEntity<>(profileService.uploadProfile(image, username, location), HttpStatus.OK);
    }

    @PutMapping(value = "/edit-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PartyPalResponse<ProfileResponse>> editProfile(@RequestParam("image") MultipartFile image,
                                                                         @RequestParam("details") String details){
        ImageFileValidator.isValid(image);
        return new ResponseEntity<>(profileService.editProfile(image, details), HttpStatus.OK);
    }

    @GetMapping("/get-user-profile")
    public ResponseEntity<PartyPalResponse<ProfileResponse>> getLoggedProfile(){
        return new ResponseEntity<>(profileService.getLoggedInProfile(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user-profile")
    public ResponseEntity<PartyPalResponse<String>> deleteUserAccount(){
        return new ResponseEntity<>(profileService.deleteAccount(), HttpStatus.OK);
    }
}
