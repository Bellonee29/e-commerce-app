package org.partypal.userManagement.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "otp_s")
public class Otp {
    @Id
    private String id;
    @Indexed(name = "otp_index")
    private String otp;
    private Date expiration;
    @DBRef
    private User user;
}
