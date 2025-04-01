package org.partypal.emailNotification.events;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.commonModule.events.Event;
import org.partypal.commonModule.events.EventHandler;
import org.partypal.emailNotification.config.RestTemplateConfig;
import org.partypal.emailNotification.models.SenderModel;
import org.partypal.emailNotification.models.TransactionalEmail;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCreatedMailHandler implements EventHandler {
    private final RestTemplateConfig restTemplateConfig;
    @Override
    public void handleEvent(Event event) {
        EventEmailObject emailObject = (EventEmailObject) event.getEventDetails();
        String subject = emailObject.getSubject();
        String emailContent = emailObject.getEmailContent();
        List<String> userEmails = emailObject.getUserEmails();
        CompletableFuture<?> future = sendEventCreationMail(userEmails, subject, emailContent);
        future.join();
        log.info("Mail Sent Successfully to: {}", userEmails);
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultSendWishlistMail")
    public CompletableFuture<?> sendEventCreationMail(List<String> userEmails, String subject, String emailContent){
        List<SenderModel> senderModels = new ArrayList<>();
        int i = 0;
        for(String email : userEmails){
            senderModels.add(SenderModel.builder()
                            .name("customer "+i)
                            .email(email)
                    .build());
            i++;
        }

        return CompletableFuture.runAsync(()->{
            TransactionalEmail transactionalEmail = TransactionalEmail.builder()
                    .htmlContent(emailContent)
                    .sender(SenderModel.builder()
                            .name("Party Pal Ent.")
                            .email("partypalent@gmail.com")
                            .build())
                    .to(new HashSet<>(senderModels))
                    .subject(subject)
                    .build();

            HttpEntity<TransactionalEmail> httpEntity = new HttpEntity<>(transactionalEmail);
            ResponseEntity<String> response;
            try{
                response = restTemplateConfig.restTemplate().exchange("/smtp/email", HttpMethod.POST,httpEntity, String.class);
                if(response.getStatusCode().is2xxSuccessful()){
                    log.info("Mail sent successfully");
                }else{
                    throw new RuntimeException("Error in sending mail");
                }
            }catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<?> getDefaultEventCreationMail(List<String> userEmails, String subject, String emailContent) {
        CompletableFuture<String> result = new CompletableFuture<>();
        result.complete("Email Sending Failed, Maximum trials Exceeded");
        return result;
    }
}
