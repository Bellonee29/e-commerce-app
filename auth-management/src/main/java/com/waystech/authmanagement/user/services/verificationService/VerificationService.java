package com.waystech.authmanagement.user.services.verificationService;


import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.VerificationRequest;
import com.waystech.authmanagement.user.dto.response.SignInResponse;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface VerificationService {
    NovaResponse<SignInResponse> verifyRegistrationOTP(VerificationRequest verificationRequest);

    NovaResponse<SignUpResponse> verifyPasswordOTP(VerificationRequest request);

    NovaResponse<String> resendRegistrationOTP(String email);

    NovaResponse<String> resendPasswordOTP(String email);
}
