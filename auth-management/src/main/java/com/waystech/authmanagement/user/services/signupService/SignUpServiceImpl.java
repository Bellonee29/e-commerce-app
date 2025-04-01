package com.waystech.authmanagement.user.services.signupService;

import com.waystech.authmanagement.Utils.OtpGenerator;
import com.waystech.authmanagement.emailNotification.events.EmailEvent;
import com.waystech.authmanagement.emailNotification.models.EmailModels;
import com.waystech.authmanagement.exceptions.classes.UserAlreadyExistException;
import com.waystech.authmanagement.integrations.asyncService.services.registrationService.AsyncRegistrationService;
import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.SignUpRequest;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import com.waystech.authmanagement.user.dto.response.UserDto;
import com.waystech.authmanagement.user.enums.Role;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AsyncRegistrationService asyncRegistrationService;

    @Override
    public NovaResponse<SignUpResponse> registerUser(SignUpRequest request){
        if(userRepository.existsByEmailOrPhoneNumber
                (request.getEmail(), request.getPhoneNumber())){
            throw new UserAlreadyExistException("User is already registered, Proceed to login");
        }
        User user = userRepository.save(User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .state(request.getState())
                        .address(request.getAddress())
                        .country(request.getCountry())
                        .phoneNumber(request.getPhoneNumber())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .isVerified(false)
                        .role(Role.valueOf(request.getUserType().toUpperCase()))
                .build());
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_REGISTRATION(user.getFirstname(), "Nova", otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "OTP Verification", emailContent, otpString);
        asyncRegistrationService.queueRegistrationMail(emailEvent);
        SignUpResponse response = SignUpResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .build();
        return new NovaResponse<>("User registered successfully", response);
    }
}
