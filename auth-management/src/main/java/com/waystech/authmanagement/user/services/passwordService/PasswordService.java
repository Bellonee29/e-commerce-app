package com.waystech.authmanagement.user.services.passwordService;


import com.waystech.authmanagement.user.dto.NovaResponse;
import com.waystech.authmanagement.user.dto.request.ForgotPasswordRequest;
import com.waystech.authmanagement.user.dto.response.SignUpResponse;
import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    NovaResponse<SignUpResponse> forgotPassword(String email);

    NovaResponse<String> resetPassword(ForgotPasswordRequest request);
}
