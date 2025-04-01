package org.partypal.userManagement.domain.services.signupService;

import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.SignUpRequest;
import org.partypal.userManagement.application.dto.response.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {
    PartyPalResponse<SignUpResponse> registerUser(SignUpRequest request);
}
