package org.partypal.userManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignInResponse {
    private String userId;
    private String email;
    private String accessToken;
    private String refreshToken;

}
