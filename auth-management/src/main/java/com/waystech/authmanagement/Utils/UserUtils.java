package org.partypal.commonModule.utils;

import lombok.RequiredArgsConstructor;
import org.partypal.commonModule.exceptions.classes.UserNotFoundException;
import org.partypal.userManagement.domain.models.User;
import org.partypal.userManagement.domain.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;

    public User getUserFromContext(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UserNotFoundException("User must be logged in"));
    }
}
