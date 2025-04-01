package com.waystech.authmanagement.emailNotification.events;

import com.waystech.authmanagement.Utils.DateUtils;
import com.waystech.authmanagement.emailNotification.config.RestTemplateConfig;
import com.waystech.authmanagement.emailNotification.models.SenderModel;
import com.waystech.authmanagement.emailNotification.models.TransactionalEmail;
import com.waystech.authmanagement.user.models.Otp;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.OtpRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationMailHandler implements EventHandler {
    private final RestTemplateConfig restTemplateConfig;
    private final OtpRepository otpRepository;
    @Override
    public void handleEvent(Event event) {
        User user = event.getCreatedBy();
        EmailEvent emailEvent = (EmailEvent) event.getEventDetails();
        String subject = emailEvent.getSubject();
        String emailContent = emailEvent.getEmailContent();
        String otpString = emailEvent.getOtp();
        Otp otp;
        if (otpRepository.existsByUser(user)) {
            otp = otpRepository.findByUser(user);
            otp.setOtp(otpString);
            otp.setExpiration(DateUtils.getExpirationDate(10));
            otpRepository.save(otp);
        } else {
            otpRepository.save(Otp.builder()
                    .otp(otpString)
                    .user(user)
                    .expiration(DateUtils.getExpirationDate(10))
                    .build());
        }
        CompletableFuture<?> future = sendOTPVerification(user, subject, emailContent);
        future.join();
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultSendWishlistMail")
    public CompletableFuture<?> sendOTPVerification(User user, String subject, String emailContent ){


        return CompletableFuture.runAsync(()->{
            TransactionalEmail transactionalEmail = TransactionalEmail.builder()
                    .htmlContent(emailContent)
                    .sender(SenderModel.builder()
                            .name("Party Pal Ent.")
                            .email("partypalent@gmail.com")
                            .build())
                    .to(Set.of(SenderModel.builder()
                                    .name(user.getFirstname())
                                    .email(user.getEmail())
                            .build()))
                    .subject(subject)
                    .build();

            HttpEntity<TransactionalEmail> httpEntity = new HttpEntity<>(transactionalEmail);
            ResponseEntity<String> response;
            try{
                response = restTemplateConfig.restTemplate().exchange("/smtp/email", HttpMethod.POST,httpEntity, String.class);
                if(response.getStatusCode().is2xxSuccessful()){
                    log.info("Mail sent successfully to {}", user.getEmail());
                }else{
                    throw new RuntimeException("Error in sending mail");
                }
            }catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<?> getDefaultSendOTPVerification(User user, String subject, String emailContent) {
        CompletableFuture<String> result = new CompletableFuture<>();
        result.complete("Email Sending Failed, Maximum trials Exceeded");
        return result;
    }
}
