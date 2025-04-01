package org.partypal.userManagement.domain.services.verificationService;

import lombok.RequiredArgsConstructor;
import org.partypal.commonModule.exceptions.classes.UserUnauthorizedException;
import org.partypal.thirdPartyService.asyncService.services.registrationService.AsyncRegistrationService;
import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.VerificationRequest;
import org.partypal.userManagement.application.dto.response.SignInResponse;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.partypal.commonModule.exceptions.classes.OtpExpiredException;
import org.partypal.commonModule.exceptions.classes.OtpNotFoundException;
import org.partypal.securityService.services.JwtAuthProvider;
import org.partypal.emailNotification.events.EmailEvent;
import org.partypal.emailNotification.models.EmailModels;
import org.partypal.commonModule.utils.OtpGenerator;
import org.partypal.userManagement.application.dto.response.UserDto;
import org.partypal.userManagement.domain.models.Otp;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.OtpRepository;
import org.partypal.userManagement.domain.repository.UserRepository;
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
    public PartyPalResponse<SignInResponse> verifyRegistrationOTP(VerificationRequest verificationRequest){
        User user = userRepository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));

        Otp otp = otpRepository.findByOtpAndUser(verificationRequest.getOtp(), user)
                .orElseThrow(()-> new OtpNotFoundException("Otp does not exist"));
        if(otp.getExpiration().before(new Date())){
            throw new OtpExpiredException("Otp has expired, please request new otp");
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
        return new PartyPalResponse<>("Otp Verified Successfully", signInResponse);
    }

    @Override
    public PartyPalResponse<SignUpResponse> verifyPasswordOTP(VerificationRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));

        Otp otp = otpRepository.findByOtpAndUser(request.getOtp(), user)
                .orElseThrow(()-> new OtpNotFoundException("Otp does not exist"));
        if(otp.getExpiration().before(new Date())){
            throw new OtpExpiredException("Otp has expired, please request new otp");
        }
        return new PartyPalResponse<>("OTP verified Successfully", SignUpResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .build());
    }
    @Override
    public PartyPalResponse<String> resendRegistrationOTP(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_REGISTRATION(user.getFirstname(), "PARTY PAL", otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "OTP Verification", emailContent, otpString);
        asyncRegistrationService.queueResendOtpMail(emailEvent);
        return new PartyPalResponse<>("Request made successfully", "Please Check your mail for new OTP");
    }

    @Override
    public PartyPalResponse<String> resendPasswordOTP(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User does not exist"));
        if(!user.getIsVerified()){
            throw new UserUnauthorizedException("User Must Verify Account First");
        }
        String otpString = OtpGenerator.generateOtpString();
        String emailContent = EmailModels.OTP_PASSWORD(user.getFirstname(), otpString);
        EmailEvent emailEvent = new EmailEvent(new UserDto(user), "OTP Verification", emailContent, otpString);
        asyncRegistrationService.queueResendOtpMail(emailEvent);
        return new PartyPalResponse<>("Request made successfully", "Please Check your mail for new OTP");
    }
}
