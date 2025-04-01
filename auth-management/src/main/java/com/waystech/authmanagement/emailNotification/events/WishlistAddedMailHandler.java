package org.partypal.emailNotification.events;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.commonModule.events.Event;
import org.partypal.commonModule.events.EventHandler;
import org.partypal.emailNotification.config.RestTemplateConfig;
import org.partypal.emailNotification.models.CreateContact;
import org.partypal.emailNotification.models.SendingBlueEmail;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistAddedMailHandler implements EventHandler {

    private final RestTemplateConfig restTemplateConfig;
    @Override
    public void handleEvent(Event event) {
        EmailEvent emailEvent = (EmailEvent) event.getEventDetails();
        String to = emailEvent.getOtp();

        CompletableFuture<?> future = sendWishListMail(to);
        future.join();
        log.info("Mail Sent Successfully to: {}", to);
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultSendWishlistMail")
    public CompletableFuture<?> sendWishListMail(String to){


        return CompletableFuture.runAsync(()->{
            CreateContact createContact = CreateContact.builder()
                    .emailBlacklisted(false)
                    .updateEnabled(true)
                    .smsBlacklisted(false)
                    .listIds(Collections.singleton(7))
                    .email(to)
                    .ext_id("Wishlist-"+ UUID.randomUUID())
                    .build();

            HttpEntity<CreateContact> httpEntity = new HttpEntity<>(createContact);
            ResponseEntity<String> response;
            try{
                response = restTemplateConfig.restTemplate().exchange("/contacts", HttpMethod.POST,httpEntity, String.class);
                if(response.getStatusCode().is2xxSuccessful()){
                    SendingBlueEmail sendingBlueEmail = SendingBlueEmail.builder()
                            .emailTo(Collections.singleton(to))
                            .build();
                    HttpEntity<SendingBlueEmail> http = new HttpEntity<>(sendingBlueEmail);
                    try{
                        response = restTemplateConfig.restTemplate().exchange("/smtp/templates/1/sendTest", HttpMethod.POST, http, String.class);
                    }catch(Exception e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }else{
                    throw new RuntimeException("Error in adding contact");
                }
            }catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<?> getDefaultSendWishlistMail(String to) {
        CompletableFuture<String> result = new CompletableFuture<>();
        result.complete("Email Sending Failed, Maximum trials Exceeded");
        return result;
    }
}
