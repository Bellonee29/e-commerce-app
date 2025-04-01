package org.partypal.userManagement.domain.services.verificationService;

import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.VerificationRequest;
import org.partypal.userManagement.application.dto.response.SignInResponse;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface VerificationService {
    PartyPalResponse<SignInResponse> verifyRegistrationOTP(VerificationRequest verificationRequest);

    PartyPalResponse<SignUpResponse> verifyPasswordOTP(VerificationRequest request);

    PartyPalResponse<String> resendRegistrationOTP(String email);

    PartyPalResponse<String> resendPasswordOTP(String email);
}
