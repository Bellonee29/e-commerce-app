package com.waystech.authmanagement.user.services.signupService;

import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.SignUpRequest;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {
    NovaResponse<SignUpResponse> registerUser(SignUpRequest request);
}
