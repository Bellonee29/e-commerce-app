package org.partypal.commonModule.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.partypal.commonModule.exceptions.classes.CustomValidationException;
import org.partypal.commonModule.utils.PasswordChecker;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Check password against banned passwords list
        if(!PasswordChecker.bannedPasswords.contains(password)){
            return true;
        }else{
            throw new CustomValidationException("Common Password, Please use stronger password");
        }
    }


}

