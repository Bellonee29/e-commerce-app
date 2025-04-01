package org.partypal.userManagement.domain.services.passwordService;

import lombok.RequiredArgsConstructor;
import org.partypal.commonModule.exceptions.classes.UserUnauthorizedException;
import org.partypal.thirdPartyService.asyncService.services.registrationService.AsyncRegistrationService;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.ForgotPasswordRequest;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.partypal.commonModule.exceptions.classes.InvalidCredentialsException;
import org.partypal.commonModule.exceptions.classes.UserNotFoundException;
import org.partypal.emailNotification.events.EmailEvent;
import org.partypal.emailNotification.models.EmailModels;
import org.partypal.commonModule.utils.OtpGenerator;
import org.partypal.userManagement.application.dto.response.UserDto;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService{
    private final UserRepository userRepository;
    private final AsyncRegistrationService asyncRegistrationService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public PartyPalResponse<SignUpResponse> forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));
        if(!user.getIsVerified()){
            throw new UserUnauthorizedException("User Must Verify Account First");
        }
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_PASSWORD(user.getFirstname(), otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "Forgot Password", emailContent, otpString);
        asyncRegistrationService.queueForgotPasswordMail(emailEvent);
        SignUpResponse signUpResponse = SignUpResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .build();
        return new PartyPalResponse<>("Successful, Check your mail for reset OTP", signUpResponse);
    }

    @Override
    public PartyPalResponse<String> resetPassword(ForgotPasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));
        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Use a different password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new PartyPalResponse<>("Request Processed", "Password Changed Successfully");
    }
}
