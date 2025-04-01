package com.waystech.authmanagement.user.services.loginService;

import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.SignInRequest;
import com.waystech.authmanagement.user.dto.response.SignInResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    NovaResponse<SignInResponse> loginUser(SignInRequest request);
}
