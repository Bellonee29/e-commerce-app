package org.partypal.thirdPartyService.asyncService.services.eventService;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.partypal.emailNotification.events.EmailEvent;
import org.partypal.emailNotification.events.EventEmailObject;
import org.partypal.thirdPartyService.asyncService.model.QueueProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncEventServiceImpl implements AsyncEventService{
    private final static Gson gson = new Gson();
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void queueEventCreationMail(EventEmailObject eventEmailObject){
        log.info("Email Event Obtained: {}",eventEmailObject);
        String emailDetails = gson.toJson(eventEmailObject);
        try{
            rabbitTemplate.convertAndSend(QueueProperties.REGISTRATION_EXCHANGE.getName(),
                    QueueProperties.EVENT_CREATION_ROUTE.getName(), emailDetails);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("Message Queued Successfully");
    }

    @Override
    public void queueWishlistMail(EmailEvent emailEvent){
        log.info("Email Event Obtained: {}", emailEvent);
        String emailDetails = gson.toJson(emailEvent);
        try{
            rabbitTemplate.convertAndSend(QueueProperties.REGISTRATION_EXCHANGE.getName(),
                    QueueProperties.WISHLIST_ROUTE.getName(), emailDetails);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("Message Queued Successfully");
    }
}
