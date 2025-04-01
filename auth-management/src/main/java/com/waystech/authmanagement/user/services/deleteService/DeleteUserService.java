package org.partypal.userManagement.domain.services.deleteService;

import lombok.RequiredArgsConstructor;
import org.partypal.commonModule.exceptions.classes.UserNotFoundException;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.OtpRepository;
import org.partypal.userManagement.domain.repository.UserRepository;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserService extends AbstractMongoEventListener<User> {
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<User> event) {
        String userId = event.getSource().get("_id").toString();
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        otpRepository.deleteByUser(user);
    }
}
