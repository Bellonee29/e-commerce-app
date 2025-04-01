package com.waystech.authmanagement.user.dto.request;

import com.waystech.authmanagement.validations.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
@Builder
public class SignInRequest {
    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email address must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @ValidPassword(message = "Password must be a strong password")
    private String password;
}
