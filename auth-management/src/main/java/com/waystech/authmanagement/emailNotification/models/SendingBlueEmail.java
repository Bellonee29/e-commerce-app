package com.waystech.authmanagement.emailNotification.models;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendingBlueEmail {
    private Set<String> emailTo;
}
