package org.partypal.userManagement.domain.services.passwordService;

import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.ForgotPasswordRequest;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    PartyPalResponse<SignUpResponse> forgotPassword(String email);

    PartyPalResponse<String> resetPassword(ForgotPasswordRequest request);
}
