package com.waystech.authmanagement.user.services.passwordService;

import com.waystech.authmanagement.Utils.OtpGenerator;
import com.waystech.authmanagement.emailNotification.events.EmailEvent;
import com.waystech.authmanagement.emailNotification.models.EmailModels;
import com.waystech.authmanagement.exceptions.classes.InvalidCredentialsException;
import com.waystech.authmanagement.exceptions.classes.UserNotFoundException;
import com.waystech.authmanagement.exceptions.classes.UserUnauthorizedException;
import com.waystech.authmanagement.integrations.asyncService.services.registrationService.AsyncRegistrationService;
import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.ForgotPasswordRequest;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import com.waystech.authmanagement.user.dto.response.UserDto;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService{
    private final UserRepository userRepository;
    private final AsyncRegistrationService asyncRegistrationService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public NovaResponse<SignUpResponse> forgotPassword(String email){
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
        return new NovaResponse<>("Successful, Check your mail for reset OTP", signUpResponse);
    }

    @Override
    public NovaResponse<String> resetPassword(ForgotPasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));
        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Use a different password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new NovaResponse<>("Request Processed", "Password Changed Successfully");
    }
}
