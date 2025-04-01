package org.partypal.userManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.partypal.userManagement.domain.models.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto{
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String role;
    public UserDto(User user){
        this.userId = user.getUserId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().name();
    }
}
