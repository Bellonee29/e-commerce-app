package com.waystech.authmanagement.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EditProfileRequest {
    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 3, message = "Firstname must be minimum of 3 characters")
    private String firstname;
    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 3, message = "Firstname must be minimum of 3 characters")
    private String lastname;
    @NotBlank(message = "Firstname must not be blank")
    @Size(min = 3, message = "Firstname must be minimum of 3 characters")
    private String username;
    @NotBlank(message = "Location must not be blank")
    private String location;
}
