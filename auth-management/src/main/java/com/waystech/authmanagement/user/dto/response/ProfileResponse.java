package org.partypal.userManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.partypal.userManagement.domain.models.User;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String username;
    private Date birthday;
    private String location;
    private String imageUrl;
    private String role;
    private Date createdDate;
    private Date updatedAt;

    public ProfileResponse(User user){
        this.id = user.getUserId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.username = user.getUsername();
        this.location = user.getLocation();
        this.imageUrl = user.getImageUrl();
        this.role = user.getRole().name();
        this.createdDate = user.getDateCreated();
        this.updatedAt = user.getUpdatedAt();
    }
}
