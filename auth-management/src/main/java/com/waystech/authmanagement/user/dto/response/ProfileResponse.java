package com.waystech.authmanagement.user.dto.response;

import com.waystech.authmanagement.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private String imageUrl;
    private String role;

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
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.modifiedBy = user.getModifiedBy();
    }
}
