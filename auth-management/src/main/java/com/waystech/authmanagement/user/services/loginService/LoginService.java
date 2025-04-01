package org.partypal.userManagement.domain.services.loginService;

import org.partypal.userManagement.application.dto.PartyPalResponse;
import org.partypal.userManagement.application.dto.request.SignInRequest;
import org.partypal.userManagement.application.dto.response.SignInResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    PartyPalResponse<SignInResponse> loginUser(SignInRequest request);
}
