package org.partypal.userManagement.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class VerificationRequest {
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "OTP must not be blank")
    private String otp;
}
