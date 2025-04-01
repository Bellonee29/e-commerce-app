package com.waystech.authmanagement.emailNotification.models;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionalEmail {
    private SenderModel sender;
    private Set<SenderModel> to;
    private Set<SenderModel> bcc;
    private Set<SenderModel> cc;
    private String htmlContent;
    private String subject;
    private SenderModel replyTo;
    private Set<String> tags;

}
