package com.waystech.authmanagement.Utils;

import com.waystech.authmanagement.exceptions.classes.UserNotFoundException;
import com.waystech.authmanagement.user.models.User;
import com.waystech.authmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
