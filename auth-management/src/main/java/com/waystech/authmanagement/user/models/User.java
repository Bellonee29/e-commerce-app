package com.waystech.authmanagement.user.models;

import com.waystech.authmanagement.user.enums.Role;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User extends BaseEntity {
    @Id
    private String userId;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "username",unique = true)
    private String username;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    private String address;
    private String state;
    private String country;
    private String password;
    private String location;
    private String imageUrl;
    private Boolean isVerified;
    private Role role;

    @PostConstruct
    public void init(){
        if(this.userId == null){
            this.userId = UUID.randomUUID().toString();
        }
    }

}
