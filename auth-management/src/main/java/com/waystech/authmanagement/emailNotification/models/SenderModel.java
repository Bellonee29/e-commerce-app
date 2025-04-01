package com.waystech.authmanagement.emailNotification.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SenderModel {
    private String name;
    private String email;
}
