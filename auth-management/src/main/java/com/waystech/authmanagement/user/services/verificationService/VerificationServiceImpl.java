package com.waystech.authmanagement.user.services.verificationService;

import com.waystech.authmanagement.Utils.OtpGenerator;
import com.waystech.authmanagement.emailNotification.events.EmailEvent;
import com.waystech.authmanagement.emailNotification.models.EmailModels;
import com.waystech.authmanagement.exceptions.classes.OtpExpiredException;
import com.waystech.authmanagement.exceptions.classes.OtpNotFoundException;
import com.waystech.authmanagement.exceptions.classes.UserUnauthorizedException;
import com.waystech.authmanagement.security.JwtAuthProvider;
import com.waystech.authmanagement.integrations.asyncService.services.registrationService.AsyncRegistrationService;
import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.VerificationRequest;
import com.waystech.authmanagement.user.dto.response.SignInResponse;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import com.waystech.authmanagement.user.dto.response.UserDto;
import com.waystech.authmanagement.user.models.Otp;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.OtpRepository;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService{
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final JwtAuthProvider authProvider;
    private final AsyncRegistrationService asyncRegistrationService;

    @Override
    public NovaResponse<SignInResponse> verifyRegistrationOTP(VerificationRequest verificationRequest){
        User user = userRepository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));

        Otp otp = otpRepository.findByOtpAndUser(verificationRequest.getOtp(), user)
                .orElseThrow(()-> new OtpNotFoundException("Otp does not exist"));
        if(otp.getExpiration().before(new Date())){
            throw new OtpExpiredException("Otp has expired, please request for new otp");
        }
        user.setIsVerified(true);
        userRepository.save(user);
        Authentication authentication = new
                UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SignInResponse signInResponse = SignInResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .accessToken(authProvider.generateAccessToken(authentication))
                .refreshToken(authProvider.generateRefreshToken(authentication))
                .build();
        return new NovaResponse<>("Otp Verified Successfully", signInResponse);
    }

    @Override
    public NovaResponse<SignUpResponse> verifyPasswordOTP(VerificationRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));

        Otp otp = otpRepository.findByOtpAndUser(request.getOtp(), user)
                .orElseThrow(()-> new OtpNotFoundException("Otp does not exist"));
        if(otp.getExpiration().before(new Date())){
            throw new OtpExpiredException("Otp has expired, please request new otp");
        }
        return new NovaResponse<>("OTP verified Successfully", SignUpResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .build());
    }
    @Override
    public NovaResponse<String> resendRegistrationOTP(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_REGISTRATION(user.getFirstname(), "PARTY PAL", otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "OTP Verification", emailContent, otpString);
        asyncRegistrationService.queueResendOtpMail(emailEvent);
        return new NovaResponse<>("Request made successfully", "Please Check your mail for new OTP");
    }

    @Override
    public NovaResponse<String> resendPasswordOTP(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));
        if(!user.getIsVerified()){
            throw new UserUnauthorizedException("User Must Verify Account First");
        }
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_PASSWORD(user.getFirstname(), otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "OTP Verification", emailContent, otpString);
        asyncRegistrationService.queueResendOtpMail(emailEvent);
        return new NovaResponse<>("Request made successfully", "Please Check your mail for new OTP");
    }
}
