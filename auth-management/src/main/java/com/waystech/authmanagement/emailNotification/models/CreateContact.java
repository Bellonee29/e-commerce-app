package com.waystech.authmanagement.emailNotification.models;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateContact {
    private boolean emailBlacklisted;
    private boolean smsBlacklisted;
    private Set<Integer> listIds;
    private boolean updateEnabled;
    private String ext_id;
    private String email;
}
