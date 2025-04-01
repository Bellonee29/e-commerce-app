package org.partypal.userManagement.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.partypal.commonModule.validations.ValidPassword;
import org.partypal.commonModule.validations.ValidUserType;

@AllArgsConstructor
@Data
@Builder
public class SignUpRequest {
    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 3, message = "Firstname must be minimum of 3 characters")
    private String firstname;

    @NotBlank(message = "Lastname must not be blank")
    @Size(min = 3, message = "Lastname must be minimum of 3 characters")
    private String lastname;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email address must not be blank")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;

    @NotBlank(message = "Password must not be blank")
    @ValidPassword(message = "Password must be a strong password")
    private String password;

    @NotBlank(message = "UserType must not be blank")
    @ValidUserType(message = "Must be either User or Promoter")
    private String userType;
}
