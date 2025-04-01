package com.waystech.authmanagement.user.models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.Id;


import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Otp {
    @Id
    private String id;
    @Column(name = "otp")
    private String otp;
    private Date expiration;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PostConstruct
    public void init(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }
}
