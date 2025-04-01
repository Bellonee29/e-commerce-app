package org.partypal.userManagement.domain.services.signupService;

import lombok.RequiredArgsConstructor;
import org.partypal.thirdPartyService.asyncService.services.registrationService.AsyncRegistrationService;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.SignUpRequest;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.partypal.commonModule.exceptions.classes.UserAlreadyExistException;
import org.partypal.emailNotification.events.EmailEvent;
import org.partypal.emailNotification.models.EmailModels;
import org.partypal.commonModule.utils.OtpGenerator;
import org.partypal.userManagement.application.dto.response.UserDto;
import org.partypal.userManagement.domain.enums.Role;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AsyncRegistrationService asyncRegistrationService;

    @Override
    public PartyPalResponse<SignUpResponse> registerUser(SignUpRequest request){
        if(userRepository.existsByEmailOrPhoneNumber
                (request.getEmail(), request.getPhoneNumber())){
            throw new UserAlreadyExistException("User is already registered, Proceed to login");
        }
        User user = userRepository.save(User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .isVerified(false)
                        .role(Role.valueOf(request.getUserType().toUpperCase()))
                .build());
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_REGISTRATION(user.getFirstname(), "PARTY PAL", otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "OTP Verification", emailContent, otpString);
        asyncRegistrationService.queueRegistrationMail(emailEvent);
        SignUpResponse response = SignUpResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .build();
        return new PartyPalResponse<>("User registered successfully", response);
    }
}
