package org.partypal.userManagement.domain.models;

import lombok.*;
import org.partypal.userManagement.domain.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    @Indexed(name = "firstname_index")
    private String firstname;
    @Indexed(name = "lastname_index")
    private String lastname;
    @Indexed(name = "username_index",unique = true)
    private String username;
    @Indexed(name = "email_index",unique = true)
    private String email;
    @Indexed(name = "phone_number_index",unique = true)
    private String phoneNumber;
    private String password;
    private String location;
    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date updatedAt;
    private String imageUrl;
    private Boolean isVerified;
    private Role role;

}
