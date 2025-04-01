package org.partypal.userManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class SignUpResponse {
    private String id;
    private String email;
}
