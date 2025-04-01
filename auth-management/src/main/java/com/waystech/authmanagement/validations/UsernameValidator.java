package org.partypal.commonModule.validations;

import lombok.RequiredArgsConstructor;
import org.partypal.commonModule.exceptions.classes.CustomValidationException;
import org.partypal.userManagement.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameValidator{

    private final UserRepository userRepository;
    public void checkValidity(String username){
        if(!username.matches("[a-zA-Z0-9-_]+")) {
            throw new CustomValidationException("Username must contain only alphabets, numbers and "+"- or _");
        }else if(username.length() < 3){
            throw new CustomValidationException("Username must have 3 characters minimum");
        }else if(userRepository.existsByUsername(username)){
            throw new CustomValidationException("Username already exists");
        }
    }
}
