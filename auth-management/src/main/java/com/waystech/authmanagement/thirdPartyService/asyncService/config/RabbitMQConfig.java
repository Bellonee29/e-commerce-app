package org.partypal.thirdPartyService.asyncService.config;

import org.partypal.thirdPartyService.asyncService.model.QueueProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue userRegisteredQueue(){
        return new Queue(QueueProperties.REGISTRATION_EMAIL_QUEUE.getName(), true);
    }

    @Bean
    public Queue userForgotPasswordQueue(){
        return new Queue(QueueProperties.FORGOT_PASSWORD_QUEUE.getName(), true);
    }
    @Bean
    public Queue userResendOtpQueue(){
        return new Queue(QueueProperties.RESEND_OTP_QUEUE.getName(), true);
    }

    @Bean
    public Queue userEventCreatedQueue() {
        return new Queue(QueueProperties.EVENT_CREATION_QUEUE.getName(), true);
    }

    @Bean
    public Queue userWishlistAddedQueue() {
        return new Queue(QueueProperties.WISHLIST_QUEUE.getName(), true);
    }
    @Bean
    public DirectExchange userRegisteredExchange(){
        return new DirectExchange(QueueProperties.REGISTRATION_EXCHANGE.getName(),
                true, false);
    }

    @Bean
    public Binding userRegisteredBinding(Queue userRegisteredQueue, DirectExchange userRegisteredExchange){
        return BindingBuilder.bind(userRegisteredQueue).to(userRegisteredExchange).with(QueueProperties.REGISTRATION_ROUTE.getName());
    }

    @Bean
    public Binding userForgotPasswordBinding(Queue userForgotPasswordQueue, DirectExchange userRegisteredExchange){
        return BindingBuilder.bind(userForgotPasswordQueue).to(userRegisteredExchange).with(QueueProperties.FORGOT_PASSWORD_ROUTE.getName());
    }

    @Bean
    public Binding userResendOtpBinding(Queue userResendOtpQueue, DirectExchange userRegisteredExchange){
        return BindingBuilder.bind(userResendOtpQueue).to(userRegisteredExchange).with(QueueProperties.RESEND_OTP_ROUTE.getName());
    }

    @Bean
    public Binding userEventCreatedBinding(Queue userEventCreatedQueue, DirectExchange userRegisteredExchange){
        return BindingBuilder.bind(userEventCreatedQueue).to(userRegisteredExchange).with(QueueProperties.EVENT_CREATION_ROUTE.getName());
    }

    @Bean
    public Binding userWishlistAddedBinding(Queue userWishlistAddedQueue, DirectExchange userRegisteredExchange){
        return BindingBuilder.bind(userWishlistAddedQueue).to(userRegisteredExchange).with(QueueProperties.WISHLIST_ROUTE.getName());
    }
}
